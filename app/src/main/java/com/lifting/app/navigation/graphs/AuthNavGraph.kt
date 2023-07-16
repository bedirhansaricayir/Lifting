package com.lifting.app.navigation.graphs

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lifting.app.core.constants.Constants.Companion.ONBOARDING_SCREEN
import com.lifting.app.core.constants.Constants.Companion.SIGN_IN
import com.lifting.app.core.constants.Constants.Companion.SIGN_UP
import com.lifting.app.core.constants.Constants.Companion.VERIFICATION_SCREEN
import com.lifting.app.feature_auth.presentation.AuthenticationEvent
import com.lifting.app.feature_auth.presentation.AuthenticationViewModel
import com.lifting.app.feature_auth.presentation.SignInScreen
import com.lifting.app.feature_auth.presentation.SignUpScreen
import com.lifting.app.feature_auth.presentation.email_verification.VerificationScreen
import com.lifting.app.presentation.onboarding.OnBoarding
import com.lifting.app.presentation.onboarding.OnBoardingViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.OnBoardingScreen.route
    ) {
        composable(route = AuthScreen.OnBoardingScreen.route) {
            val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
            OnBoarding(
                onGetStartedButtonClick = {
                    navController.navigate(AuthScreen.SignInScreen.route) {
                        popUpTo(AuthScreen.OnBoardingScreen.route) {
                            inclusive = true
                        }
                    }
                },
                saveOnBoardingState = {
                    onBoardingViewModel.saveOnBoardingState(it)
                }
            )
        }
        composable(route = AuthScreen.SignInScreen.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val authenticationState = authenticationViewModel.authState.collectAsState().value
            val googleSignInState = authenticationViewModel.googleState.collectAsState().value
            val forgotPasswordState = authenticationViewModel.forgotPasswordState.collectAsState().value
            val scope = rememberCoroutineScope()
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        scope.launch {
                            val signInResult = authenticationViewModel.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            authenticationViewModel.onEvent(
                                AuthenticationEvent.OnSignInResultGoogle(
                                    signInResult
                                )
                            )
                        }
                    }
                }
            )
            SignInScreen(
                authenticationState = authenticationState,
                authenticationEvent = authenticationViewModel::onEvent,
                onToggleModeClick = { navController.navigate(AuthScreen.SignUpScreen.route) },
                googleSignInState = googleSignInState,
                forgotPasswordState = forgotPasswordState,
                onGoogleSignInButtonClicked = {
                    scope.launch {
                        val signInIntentSender = authenticationViewModel.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                onSignInNavigate = {
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthScreen.SignUpScreen.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val authenticationState = authenticationViewModel.authState.collectAsState().value
            SignUpScreen(
                authenticationState = authenticationState,
                authenticationEvent = authenticationViewModel::onEvent,
                onToggleModeClick = {
                    navController.navigate(AuthScreen.SignInScreen.route){
                        navController.popBackStack()

                    }
                                    },
                onSignUpNavigate = {
                    navController.navigate(AuthScreen.EmailVerificationScreen.route){
                        navController.popBackStack()
                     }
                }
            )
        }
        composable(route = AuthScreen.EmailVerificationScreen.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val authenticationState = authenticationViewModel.authState.collectAsState().value
            VerificationScreen(
                authenticationState = authenticationState,
                authenticationEvent = authenticationViewModel::onEvent,
                isEmailVerified = {
                    navController.navigate(Graph.HOME){
                        popUpTo(Graph.AUTHENTICATION){
                            inclusive = true
                        }
                    }
                })
        }
    }
}

sealed class AuthScreen(val route: String) {
    object SignInScreen : AuthScreen(route = SIGN_IN)
    object SignUpScreen : AuthScreen(route = SIGN_UP)
    object OnBoardingScreen : AuthScreen(route = ONBOARDING_SCREEN)
    object EmailVerificationScreen : AuthScreen(route = VERIFICATION_SCREEN)
}