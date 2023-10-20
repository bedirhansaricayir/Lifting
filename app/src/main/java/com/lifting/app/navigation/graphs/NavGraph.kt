package com.lifting.app.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.lifting.app.R
import com.lifting.app.common.constants.Constants.Companion.DETAIL_SCREEN
import com.lifting.app.common.constants.Constants.Companion.NOTIFICATION_SETTINGS_SCREEN
import com.lifting.app.common.constants.Constants.Companion.PURCHASE_SCREEN
import com.lifting.app.common.constants.Constants.Companion.TOOLS_DETAILS_SCREEN
import com.lifting.app.common.constants.Constants.Companion.TOOLS_SCREEN_ARGS_KEY
import com.lifting.app.feature_home.presentation.calculator.CalculatorCategory
import com.lifting.app.navigation.Screen
import com.lifting.app.feature_home.presentation.calculator.CalculatorScreen
import com.lifting.app.feature_home.presentation.home.HomePageUiState
import com.lifting.app.feature_home.presentation.home.HomeScreen
import com.lifting.app.feature_home.presentation.home.HomeViewModel
import com.lifting.app.feature_home.presentation.home.UserDataState
import com.lifting.app.feature_home.presentation.notification.NotificationSettingsScreen
import com.lifting.app.feature_home.presentation.profile.ProfileScreen
import com.lifting.app.feature_home.presentation.profile.ProfileScreenState
import com.lifting.app.feature_home.presentation.profile.ProfileScreenViewModel
import com.lifting.app.feature_home.presentation.purchase.PurchaseScreen
import com.lifting.app.feature_home.presentation.purchase.PurchaseScreenState
import com.lifting.app.feature_home.presentation.purchase.PurchaseScreenViewModel
import com.lifting.app.feature_home.presentation.tools_detail.ToolsDetailScreen
import com.lifting.app.feature_home.presentation.tools_detail.bmi.BMIScreen
import com.lifting.app.feature_home.presentation.tools_detail.bmi.BMIScreenState
import com.lifting.app.feature_home.presentation.tools_detail.bmi.BMIViewModel
import com.lifting.app.feature_home.presentation.tools_detail.bmr.BMRScreen
import com.lifting.app.feature_home.presentation.tools_detail.bmr.BMRScreenState
import com.lifting.app.feature_home.presentation.tools_detail.bmr.BMRViewModel
import com.lifting.app.feature_home.presentation.tools_detail.one_rep.OneRepScreen
import com.lifting.app.feature_home.presentation.tools_detail.one_rep.OneRepScreenState
import com.lifting.app.feature_home.presentation.tools_detail.one_rep.OneRepViewModel
import com.lifting.app.feature_home.presentation.tracker.TrackerPageUiState
import com.lifting.app.feature_home.presentation.tracker.TrackerPageViewModel
import com.lifting.app.feature_home.presentation.tracker.TrackerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController,isPremiumUser: (Boolean) -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val state: HomePageUiState = homeViewModel.state.collectAsState().value
            val userDataState: UserDataState = homeViewModel.userDataState.collectAsState().value
            userDataState.isPremium?.let { premium -> isPremiumUser.invoke(premium) }
            HomeScreen(
                state = state,
                userState = userDataState,
                onEvent = homeViewModel::onEvent,
                onProfilePictureClicked = {
                    navController.navigate(DetailScreen.ProfileScreen.route)
                },
                onPersonalizedProgramCardClicked = {
                    navController.navigate(DetailScreen.PurchaseScreen.route)
                }
            )
        }
        composable(route = Screen.TrackerScreen.route) {
            val trackerViewModel: TrackerPageViewModel = hiltViewModel()
            val state: TrackerPageUiState = trackerViewModel.state.collectAsState().value
            TrackerScreen(
                state = state,
                onEvent = trackerViewModel::onEvent
            )
        }
        composable(route = Screen.HealthScreen.route) {
            CalculatorScreen(
                onClick = { navController.navigate(DetailScreen.ToolsDetailScreen.passArg(it.toString())) }
            )
        }
        authNavGraph(navController = navController)
        detailsNavGraph(navController = navController, onUserLogout = { isPremiumUser.invoke(false) })
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController,onUserLogout: () -> Unit) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailScreen.ProfileScreen.route
    ) {
        composable(route = DetailScreen.ProfileScreen.route) {
            val profileScreenViewModel : ProfileScreenViewModel = hiltViewModel()
            val state: ProfileScreenState = profileScreenViewModel.state.collectAsState().value
            ProfileScreen(
                state = state,
                profileScreenEvent = profileScreenViewModel::onEvent,
                onBackNavigationIconClicked = { navController.popBackStack() },
                onForwardNavigationIconClicked = { route ->
                    when(route){
                        R.string.logout -> {
                            navController.navigate(AuthScreen.SignInScreen.route) {
                                popUpTo(Graph.HOME) {
                                    inclusive = true
                                }
                                onUserLogout.invoke()
                            }
                        }
                        R.string.notification_settings -> {
                            navController.navigate(DetailScreen.NotificationSettingsScreen.route)
                        }
                    }
                }
            )
        }
        composable(route = DetailScreen.PurchaseScreen.route) {
            val purchaseScreenViewModel : PurchaseScreenViewModel = hiltViewModel()
            val state: PurchaseScreenState = purchaseScreenViewModel.state.collectAsState().value
            PurchaseScreen(
                state = state,
                onNavigationClick = { navController.popBackStack() }
            )
        }
        composable(route = DetailScreen.NotificationSettingsScreen.route) {
            NotificationSettingsScreen(
                onBackNavigationIconClicked = { navController.popBackStack() }
            )
        }
        composable(route = "tools_details_screen/{whichTool}",
            arguments = listOf(navArgument(TOOLS_SCREEN_ARGS_KEY){
            type = NavType.EnumType(CalculatorCategory::class.java)
        })) {
            val args = it.arguments?.get(TOOLS_SCREEN_ARGS_KEY)
            when((args as CalculatorCategory)) {
                CalculatorCategory.BMI -> {
                    val viewModel = viewModel<BMIViewModel>()
                    val state: BMIScreenState = viewModel.state.collectAsState().value
                    BMIScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                CalculatorCategory.BMR -> {
                    val viewModel = viewModel<BMRViewModel>()
                    val state: BMRScreenState = viewModel.state.collectAsState().value
                    BMRScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                CalculatorCategory.RM -> {
                    val viewModel = viewModel<OneRepViewModel>()
                    val state: OneRepScreenState = viewModel.state.collectAsState().value
                    OneRepScreen(
                        state = state,
                        onEvent = viewModel::onEvent)
                }

                CalculatorCategory.ZURT -> {}
            }
            //ToolsDetailScreen(args = args as CalculatorCategory)
        }
    }
}

sealed class DetailScreen(val route: String) {
    object PurchaseScreen : DetailScreen(route = PURCHASE_SCREEN)
    object ProfileScreen : DetailScreen(route = DETAIL_SCREEN)
    object NotificationSettingsScreen : DetailScreen(route = NOTIFICATION_SETTINGS_SCREEN)
    object ToolsDetailScreen : DetailScreen(route = TOOLS_DETAILS_SCREEN+TOOLS_SCREEN_ARGS_KEY) {
        fun passArg(category: String): String {
            return this.route.replace(oldValue = TOOLS_SCREEN_ARGS_KEY, newValue = category)
        }
    }

}