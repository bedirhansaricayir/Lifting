package com.lifting.app.navigation.graphs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.lifting.app.core.constants.Constants.Companion.DETAIL_SCREEN
import com.lifting.app.navigation.Screen
import com.lifting.app.presentation.calculator.CalculatorScreen
import com.lifting.app.presentation.home.HomePageUiState
import com.lifting.app.presentation.home.HomeScreen
import com.lifting.app.presentation.home.HomeViewModel
import com.lifting.app.presentation.tracker.TrackerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val state: HomePageUiState = homeViewModel.state.collectAsState().value
            HomeScreen(
                state = state,
                onEvent = homeViewModel::onEvent
            )
        }
        composable(route = Screen.TrackerScreen.route) {
            TrackerScreen()
        }
        composable(route = Screen.HealthScreen.route) {
            CalculatorScreen()
        }
        authNavGraph(navController = navController)
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailScreen.ProfileScreen.route
    ) {
        composable(route = DetailScreen.ProfileScreen.route) {
            Log.d("detailsNavGraph","Profile Screen")
        }
    }
}

sealed class DetailScreen(val route: String) {
    object ProfileScreen : DetailScreen(route = DETAIL_SCREEN)
}