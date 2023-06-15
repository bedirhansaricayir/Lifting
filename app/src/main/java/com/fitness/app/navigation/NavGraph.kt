package com.fitness.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fitness.app.presentation.home.HomePageUiState
import com.fitness.app.presentation.home.HomeScreen
import com.fitness.app.presentation.home.HomeViewModel
import com.fitness.app.presentation.onboarding.OnBoarding
import com.fitness.app.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.OnBoardingScreen.route) {
            val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
            OnBoarding(
                onGetStartedButtonClick = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.OnBoardingScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(route = Screen.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val state: HomePageUiState = homeViewModel.state.collectAsState().value
            HomeScreen(
                homeViewModel = homeViewModel,
                state = state
            )
        }
    }
}