package com.lifting.app.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.lifting.app.R
import com.lifting.app.common.constants.NavigationConstants.ACCOUNT_INFORMATION_SCREEN
import com.lifting.app.common.constants.NavigationConstants.DETAIL_SCREEN
import com.lifting.app.common.constants.NavigationConstants.NOTIFICATION_SETTINGS_SCREEN
import com.lifting.app.common.constants.NavigationConstants.PLAYER_SCREEN
import com.lifting.app.common.constants.NavigationConstants.PROGRAM_SCREEN
import com.lifting.app.common.constants.NavigationConstants.PURCHASE_SCREEN
import com.lifting.app.common.constants.NavigationConstants.TOOLS_DETAILS_SCREEN
import com.lifting.app.common.constants.NavigationConstants.TOOLS_SCREEN_ARGS_KEY
import com.lifting.app.feature_calculators.domain.model.CalculatorCategory
import com.lifting.app.navigation.Screen
import com.lifting.app.feature_calculators.presentation.CalculatorScreen
import com.lifting.app.feature_calculators.presentation.CalculatorScreenState
import com.lifting.app.feature_calculators.presentation.CalculatorScreenViewModel
import com.lifting.app.feature_home.presentation.home.HomePageUiState
import com.lifting.app.feature_home.presentation.home.HomeScreen
import com.lifting.app.feature_home.presentation.home.HomeViewModel
import com.lifting.app.feature_home.presentation.home.UserDataState
import com.lifting.app.feature_notification.presentation.NotificationSettingsScreen
import com.lifting.app.feature_profile.presentation.ProfileScreen
import com.lifting.app.feature_profile.presentation.ProfileScreenState
import com.lifting.app.feature_profile.presentation.ProfileScreenViewModel
import com.lifting.app.feature_purchase.presentation.PurchaseScreen
import com.lifting.app.feature_purchase.presentation.PurchaseScreenState
import com.lifting.app.feature_purchase.presentation.PurchaseScreenViewModel
import com.lifting.app.feature_calculators.presentation.tools_detail.bmi.BMIScreen
import com.lifting.app.feature_calculators.presentation.tools_detail.bmi.BMIScreenState
import com.lifting.app.feature_calculators.presentation.tools_detail.bmi.BMIViewModel
import com.lifting.app.feature_calculators.presentation.tools_detail.bmr.BMRScreen
import com.lifting.app.feature_calculators.presentation.tools_detail.bmr.BMRScreenState
import com.lifting.app.feature_calculators.presentation.tools_detail.bmr.BMRViewModel
import com.lifting.app.feature_calculators.presentation.tools_detail.one_rep.OneRepScreen
import com.lifting.app.feature_calculators.presentation.tools_detail.one_rep.OneRepScreenState
import com.lifting.app.feature_calculators.presentation.tools_detail.one_rep.OneRepViewModel
import com.lifting.app.feature_detail.domain.model.SelectedProgram
import com.lifting.app.feature_detail.presentation.ProgramScreen
import com.lifting.app.feature_notification.presentation.NotificationSettingsState
import com.lifting.app.feature_notification.presentation.NotificationSettingsViewModel
import com.lifting.app.feature_player.presentation.PlayerScreen
import com.lifting.app.feature_player.presentation.PlayerScreenState
import com.lifting.app.feature_player.presentation.PlayerViewModel
import com.lifting.app.feature_profile.presentation.AccountInformationScreen
import com.lifting.app.feature_tracker.presentation.TrackerPageUiState
import com.lifting.app.feature_tracker.presentation.TrackerPageViewModel
import com.lifting.app.feature_tracker.presentation.TrackerScreen

@RequiresApi(Build.VERSION_CODES.O)
@UnstableApi
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
                },
                onProgramClicked = { selectedProgram ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("program",selectedProgram)
                    navController.navigate(DetailScreen.ProgramScreen.route)
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
        composable(route = Screen.CalculatorScreen.route) {
            val calculatorViewModel: CalculatorScreenViewModel = hiltViewModel()
            val state: CalculatorScreenState = calculatorViewModel.state.collectAsState().value
            CalculatorScreen(
                state = state,
                onClick = {
                    navController.navigate(DetailScreen.ToolsDetailScreen.passArg(it.toString()))
                }
            )
        }
        authNavGraph(navController = navController)
        detailsNavGraph(navController = navController, onUserLogout = { isPremiumUser.invoke(false) })
    }
}

@UnstableApi
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController,onUserLogout: () -> Unit) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailScreen.ProfileScreen.route
    ) {
        composable(
            route = DetailScreen.ProfileScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300,
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },

        ) { entry ->
            val sharedViewModel = entry.sharedViewModel<ProfileScreenViewModel>(navController)
            val state: ProfileScreenState = sharedViewModel.state.collectAsState().value
            ProfileScreen(
                state = state,
                profileScreenEvent = sharedViewModel::onEvent,
                onBackNavigationIconClicked = { navController.popBackStack() },
                onForwardNavigationIconClicked = { route ->
                    when(route){
                        R.string.account_information -> {
                            navController.navigate(DetailScreen.AccountInformationScreen.route)
                        }
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

        composable(route = DetailScreen.ProgramScreen.route) { entry ->
            val programData = navController.previousBackStackEntry?.savedStateHandle?.get<SelectedProgram>("program")
            programData?.let { program ->
                ProgramScreen(
                    program = program,
                    onItemClick = { videoUrl ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("videoUrl",videoUrl)
                        navController.navigate(DetailScreen.PlayerScreen.route)
                    },
                    onBackNavigationIconClicked = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = DetailScreen.AccountInformationScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val sharedViewModel = entry.sharedViewModel<ProfileScreenViewModel>(navController)
            val state: ProfileScreenState = sharedViewModel.state.collectAsState().value

            LaunchedEffect(key1 = state.profileDataState.isAccountDeleted) {
                if (state.profileDataState.isAccountDeleted) {
                    navController.navigate(AuthScreen.SignInScreen.route) {
                        popUpTo(Graph.HOME) {
                            inclusive = true
                        }
                        onUserLogout.invoke()
                    }
                }
            }
            AccountInformationScreen(
                state = state,
                profileScreenEvent = sharedViewModel::onEvent,
                onBackNavigationIconClicked = { navController.popBackStack() },
            )
        }

        composable(route = DetailScreen.PlayerScreen.route) {
            val videoUrl = navController.previousBackStackEntry?.savedStateHandle?.get<String>("videoUrl")
            val viewModel: PlayerViewModel = hiltViewModel()
            val state: PlayerScreenState = viewModel.state.collectAsState().value
            videoUrl?.let {
                PlayerScreen(
                    videoUrl = videoUrl,
                    state = state,
                    onBackNavigationIconClicked = { navController.popBackStack() }
                )
            }

        }

        composable(route = DetailScreen.PurchaseScreen.route) {
            val purchaseScreenViewModel : PurchaseScreenViewModel = hiltViewModel()
            val state: PurchaseScreenState = purchaseScreenViewModel.state.collectAsState().value
            PurchaseScreen(
                state = state,
                onNavigationClick = { navController.popBackStack() }
            )
        }
        composable(
            route = DetailScreen.NotificationSettingsScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val notificationViewModel: NotificationSettingsViewModel = hiltViewModel()
            val state: NotificationSettingsState = notificationViewModel.state.collectAsState().value
            NotificationSettingsScreen(
                state = state,
                onEvent = notificationViewModel::onEvent,
                onBackNavigationIconClicked = { navController.popBackStack() }
            )
        }

        composable(route = "tools_details_screen/{whichTool}",
            arguments = listOf(navArgument(TOOLS_SCREEN_ARGS_KEY){
            type = NavType.EnumType(CalculatorCategory::class.java) }
            )
        ) {
            val args = it.arguments?.get(TOOLS_SCREEN_ARGS_KEY)
            val popBackStack: () -> Unit = { navController.popBackStack() }

            when((args as CalculatorCategory)) {
                CalculatorCategory.BMI -> {
                    val viewModel = viewModel<BMIViewModel>()
                    val state: BMIScreenState = viewModel.state.collectAsState().value
                    BMIScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onBackNavigationIconClicked = popBackStack
                    )
                }
                CalculatorCategory.BMR -> {
                    val viewModel = viewModel<BMRViewModel>()
                    val state: BMRScreenState = viewModel.state.collectAsState().value
                    BMRScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onBackNavigationIconClicked = popBackStack
                    )
                }
                CalculatorCategory.RM -> {
                    val viewModel = viewModel<OneRepViewModel>()
                    val state: OneRepScreenState = viewModel.state.collectAsState().value
                    OneRepScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onBackNavigationIconClicked = popBackStack
                    )
                }

            }
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
    object AccountInformationScreen : DetailScreen(route = ACCOUNT_INFORMATION_SCREEN)
    object PlayerScreen : DetailScreen(route = PLAYER_SCREEN)
    object ProgramScreen: DetailScreen(route = PROGRAM_SCREEN)

}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}