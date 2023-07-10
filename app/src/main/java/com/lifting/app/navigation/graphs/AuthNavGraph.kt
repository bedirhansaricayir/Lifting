package com.lifting.app.navigation.graphs

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lifting.app.core.constants.Constants.Companion.FORGOT_PASS
import com.lifting.app.core.constants.Constants.Companion.ONBOARDING_SCREEN
import com.lifting.app.core.constants.Constants.Companion.SIGN_IN
import com.lifting.app.core.constants.Constants.Companion.SIGN_UP
import com.lifting.app.feature_auth.presentation.AuthenticationViewModel
import com.lifting.app.feature_auth.presentation.SignInScreen
import com.lifting.app.feature_auth.presentation.SignUpScreen
import com.lifting.app.presentation.onboarding.OnBoarding
import com.lifting.app.presentation.onboarding.OnBoardingViewModel

fun NavGraphBuilder.authNavGraph(navController: NavController,startDestination: String? = null,destination: (route: String) -> Unit) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = startDestination ?: AuthScreen.OnBoardingScreen.route
    ){
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
                SignInScreen(
                    authenticationState = authenticationState,
                    authenticationEvent = authenticationViewModel::onEvent,
                    onToggleModeClick = { navController.navigate(AuthScreen.SignUpScreen.route) },
                    googleSignInState = googleSignInState
                )
            //CommonAuthentication(authenticationState = authenticationState, authenticationEvent = authenticationViewModel::onEvent, onToggleModeClick = {})

        }
        composable(route = AuthScreen.SignUpScreen.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val authenticationState = authenticationViewModel.authState.collectAsState().value
            SignUpScreen(
                authenticationState = authenticationState,
                authenticationEvent = authenticationViewModel::onEvent,
                onToggleModeClick = {navController.navigate(AuthScreen.SignInScreen.route)}
            )
        }
        composable(route = AuthScreen.ForgotPassScreen.route) {
            Log.d("authNavGraph","Forgot Screen")
        }

    }
}

sealed class AuthScreen(val route: String) {
    object SignInScreen : AuthScreen(route = SIGN_IN)
    object SignUpScreen : AuthScreen(route = SIGN_UP)
    object ForgotPassScreen : AuthScreen(route = FORGOT_PASS)
    object OnBoardingScreen : AuthScreen(route = ONBOARDING_SCREEN)
}