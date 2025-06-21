package com.lifting.app.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lifting.app.MainScreenState
import com.lifting.app.MainScreenViewModel
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.keyboard.LiftingKeyboardHost
import com.lifting.app.core.navigation.BottomSheetLayout
import com.lifting.app.core.navigation.LiftingBottomBar
import com.lifting.app.core.navigation.NavHostControllerHost
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.NavBarScreen
import com.lifting.app.core.service.isRunning
import com.lifting.app.core.ui.common.LocalAppSettings
import com.lifting.app.core.ui.components.in_app_notification.NotificationManagerHost
import com.lifting.app.core.ui.extensions.dashedBorder
import com.lifting.app.feature.barbell_selector.navigation.barbellSelectorBottomSheetScreen
import com.lifting.app.feature.barbell_selector.navigation.navigateToBarbellSelector
import com.lifting.app.feature.calendar.navigation.calendarScreen
import com.lifting.app.feature.calendar.navigation.navigateToCalendar
import com.lifting.app.feature.create_exercise.navigation.createExerciseBottomSheetScreen
import com.lifting.app.feature.create_exercise.navigation.navigateToCreateExercise
import com.lifting.app.feature.exercise_detail.navigation.exerciseDetailScreen
import com.lifting.app.feature.exercise_detail.navigation.navigateToExerciseDetail
import com.lifting.app.feature.exercises.navigation.exercisesScreen
import com.lifting.app.feature.exercises.navigation.navigateToExercisesBottomSheet
import com.lifting.app.feature.exercises_category.navigation.exercisesCategoryBottomSheetScreen
import com.lifting.app.feature.exercises_category.navigation.navigateToExercisesCategory
import com.lifting.app.feature.exercises_muscle.navigation.exercisesMuscleBottomSheetScreen
import com.lifting.app.feature.exercises_muscle.navigation.navigateToExercisesMuscle
import com.lifting.app.feature.history.navigation.historyScreen
import com.lifting.app.feature.rest_timer.navigation.navigateToRestTimer
import com.lifting.app.feature.rest_timer.navigation.restTimerBottomSheetScreen
import com.lifting.app.feature.settings.navigation.settingsScreen
import com.lifting.app.feature.superset_selector.navigation.navigateToSupersetSelector
import com.lifting.app.feature.superset_selector.navigation.supersetSelectorBottomSheetScreen
import com.lifting.app.feature.workout.navigation.workoutScreen
import com.lifting.app.feature.workout_detail.navigation.navigateToWorkoutDetail
import com.lifting.app.feature.workout_detail.navigation.workoutDetailScreen
import com.lifting.app.feature.workout_edit.navigation.navigateToWorkoutEdit
import com.lifting.app.feature.workout_edit.navigation.workoutEditScreen
import com.lifting.app.feature.workout_panel.components.PanelTopCollapsed
import com.lifting.app.feature.workout_panel.components.PanelTopExpanded
import com.lifting.app.feature.workout_panel.navigation.WorkoutPanel
import com.lifting.app.feature.workout_template_preview.navigation.navigateToWorkoutTemplatePreview
import com.lifting.app.feature.workout_template_preview.navigation.workoutTemplatePreviewScreen
import kotlinx.coroutines.launch

/**
 * Created by bedirhansaricayir on 03.08.2024
 */

internal const val animDuration = 250

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LiftingNavHost(
    mainScreenState: MainScreenState,
) {
    val mainViewModel: MainScreenViewModel = hiltViewModel()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val swipeableState = rememberSwipeableState(0)
    val coroutineScope = rememberCoroutineScope()
    val currentWorkoutId = mainScreenState.currentWorkoutId
    val panelHidden by rememberSaveable(currentWorkoutId) {
        mutableStateOf(currentWorkoutId == NONE_WORKOUT_ID)
    }

    LaunchedEffect(key1 = panelHidden) {
        swipeableState.animateTo(if (panelHidden) 0 else 1)
    }

    BackHandler(enabled = !panelHidden) {
        coroutineScope.launch {
            swipeableState.animateTo(0)
        }
    }

    NotificationManagerHost {
        NavHostControllerHost(navController) {
            CompositionLocalProvider(
                LocalAppSettings provides mainScreenState.appSettings
            ) {
                LiftingKeyboardHost {
                    BottomSheetLayout(
                        modifier = Modifier,
                        bottomSheetNavigator = bottomSheetNavigator
                    ) {
                        MainScreenScaffold(
                            panelHidden = panelHidden,
                            swipeableState = swipeableState,
                            bottomBar = {
                                LiftingBottomBar(navController = navController)
                            },
                            panel = {
                                WorkoutPanel(
                                    navigateToExercises = navController::navigateToExercisesBottomSheet,
                                    navigateToBarbellSelector = navController::navigateToBarbellSelector,
                                    navigateToSupersetSelector = navController::navigateToSupersetSelector
                                )
                            },
                            panelTopCollapsed = { PanelTopCollapsed() },
                            panelTopExpanded = {
                                val serviceState by mainViewModel.serviceState.collectAsStateWithLifecycle()
                                PanelTopExpanded(
                                    elapsedTime = serviceState.elapsedTime,
                                    totalTime = serviceState.totalTime,
                                    isRunning = serviceState.timerState.isRunning(),
                                    timeString = serviceState.timeString,
                                    onTimerBtnClicked = navController::navigateToRestTimer,
                                    onCollapseButtonClicked = {
                                        coroutineScope.launch {
                                            swipeableState.animateTo(0)
                                        }
                                    }
                                )
                            }
                        ) {
                            NavHost(
                                modifier = Modifier
                                    .background(LiftingTheme.colors.background),
                                navController = navController,
                                startDestination = NavBarScreen.Dashboard,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(animDuration)) + scaleIn(
                                        animationSpec = tween(animDuration),
                                        initialScale = 0.75f
                                    )
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(animDuration))
                                },
                                popEnterTransition = {
                                    fadeIn(animationSpec = tween(animDuration / 1)) + scaleIn(
                                        animationSpec = tween(animDuration / 1),
                                        initialScale = 0.9f
                                    )
                                },
                                popExitTransition = {
                                    fadeOut(animationSpec = tween(animDuration)) + scaleOut(
                                        animationSpec = tween(animDuration),
                                        targetScale = 0.75f
                                    )
                                }
                            ) {
                                dashboardRoot(navController)
                                historyRoot(navController)
                                workoutRoot(navController)
                                exercisesRoot(navController)
                                settingsRoot(navController)

                                addExercisesBottomSheet(navController)
                                barbellSelectorBottomSheet(navController)
                                superSetSelectorBottomSheet(navController)
                                restTimerBottomSheet()

                            }
                        }
                    }
                }
            }
        }
    }
}


private fun NavGraphBuilder.dashboardRoot(navController: NavController) {
    navigation<NavBarScreen.Dashboard>(
        startDestination = LiftingScreen.Dashboard
    ) {
        dashboard(navController)
    }
}

private fun NavGraphBuilder.historyRoot(navController: NavController) {
    navigation<NavBarScreen.History>(
        startDestination = LiftingScreen.History
    ) {
        historyScreen(
            navigateToCalendar = navController::navigateToCalendar,
            navigateToWorkoutDetail = navController::navigateToWorkoutDetail
        )
        calendarScreen(
            onNavigateBack = navController::popBackStack,
            onNavigateToWorkoutDetail = navController::navigateToWorkoutDetail
        )
        workoutDetailScreen(
            onNavigateBack = navController::popBackStack,
            onNavigateToWorkoutEdit = navController::navigateToWorkoutEdit
        )
        workoutEditScreen(
            navigateToExercises = navController::navigateToExercisesBottomSheet,
            navigateToBack = navController::popBackStack,
            navigateToBarbellSelector = navController::navigateToBarbellSelector,
            navigateToSupersetSelector = navController::navigateToSupersetSelector
        )
    }
}

private fun NavGraphBuilder.workoutRoot(navController: NavController) {
    navigation<NavBarScreen.Workout>(
        startDestination = LiftingScreen.Workout
    ) {
        workoutScreen(
            navController = navController,
            onNavigateToWorkoutEdit = navController::navigateToWorkoutEdit,
            onNavigateToWorkoutTemplatePreview = navController::navigateToWorkoutTemplatePreview
        )
        workoutEditScreen(
            navigateToExercises = navController::navigateToExercisesBottomSheet,
            navigateToBack = navController::popBackStack,
            navigateToBarbellSelector = navController::navigateToBarbellSelector,
            navigateToSupersetSelector = navController::navigateToSupersetSelector
        )
        workoutTemplatePreviewScreen(
            onNavigateToWorkoutEdit = navController::navigateToWorkoutEdit,
            popBackStack = navController::popBackStack
        )
    }
}

private fun NavGraphBuilder.exercisesRoot(navController: NavController) {
    navigation<NavBarScreen.Exercises>(
        startDestination = LiftingScreen.Exercises
    ) {
        exercisesScreen(
            navController = navController,
            onAddClick = navController::navigateToCreateExercise,
            isBottomSheet = false,
            navigateToDetail = navController::navigateToExerciseDetail
        )
        exerciseDetailScreen(popBackStack = navController::popBackStack)
        createExercisesBottomSheet(navController)
        exercisesCategoryBottomSheet(navController)
        exercisesMuscleBottomSheet(navController)
    }
}

private fun NavGraphBuilder.settingsRoot(navController: NavController) {
    navigation<NavBarScreen.Settings>(
        startDestination = LiftingScreen.Settings
    ) {
        settingsScreen()
    }
}

private fun NavGraphBuilder.dashboard(navController: NavController) {
    composable<LiftingScreen.Dashboard> {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(20) {
                    Text("Item $it",modifier = Modifier.fillMaxWidth().padding(16.dp), color = LiftingTheme.colors.onBackground)
                }

                item {
                    Box(
                        modifier = Modifier.size(250.dp)
                            .dashedBorder(
                                width = 2.dp,
                                color = LiftingTheme.colors.primary,
                                shape = LiftingTheme.shapes.medium, on = 4.dp, off = 4.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("This is a dashed border", color = LiftingTheme.colors.onBackground)
                    }
                }



                items(20) {
                    Text("Item $it",modifier = Modifier.fillMaxWidth().padding(16.dp), color = LiftingTheme.colors.onBackground)
                }
            }
        }
    }
}

private fun NavGraphBuilder.createExercisesBottomSheet(navController: NavController) {
    createExerciseBottomSheetScreen(
        navController = navController,
        onNavigateBack = navController::popBackStack,
        onNavigateToCategories = navController::navigateToExercisesCategory,
        onNavigateToMuscles = navController::navigateToExercisesMuscle
    )
}

private fun NavGraphBuilder.addExercisesBottomSheet(navController: NavController) {
    exercisesScreen(
        navController = navController,
        onAddClick = navController::navigateToCreateExercise,
        isBottomSheet = true,
        navigateToDetail = {}
    )
}
private fun NavGraphBuilder.exercisesCategoryBottomSheet(navController: NavController) {
    exercisesCategoryBottomSheetScreen(navController)
}

private fun NavGraphBuilder.exercisesMuscleBottomSheet(navController: NavController) {
    exercisesMuscleBottomSheetScreen(navController)
}
private fun NavGraphBuilder.barbellSelectorBottomSheet(navController: NavController) {
    barbellSelectorBottomSheetScreen(navController)
}

private fun NavGraphBuilder.superSetSelectorBottomSheet(navController: NavController) {
    supersetSelectorBottomSheetScreen(navController)
}

private fun NavGraphBuilder.restTimerBottomSheet() {
    restTimerBottomSheetScreen()
}

