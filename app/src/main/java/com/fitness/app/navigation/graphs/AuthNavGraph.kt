package com.fitness.app.navigation.graphs

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fitness.app.core.constants.Constants.Companion.FORGOT_PASS
import com.fitness.app.core.constants.Constants.Companion.ONBOARDING_SCREEN
import com.fitness.app.core.constants.Constants.Companion.SIGN_IN
import com.fitness.app.core.constants.Constants.Companion.SIGN_UP
import com.fitness.app.presentation.onboarding.OnBoarding
import com.fitness.app.presentation.onboarding.OnBoardingViewModel

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
            Log.d("authNavGraph","Login Screen")
        }
        composable(route = AuthScreen.SignUpScreen.route) {
            Log.d("authNavGraph","Register Screen")
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