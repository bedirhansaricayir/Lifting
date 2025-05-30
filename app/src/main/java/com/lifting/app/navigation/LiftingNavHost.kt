package com.lifting.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.navigation.LiftingBottomBar
import com.lifting.app.core.navigation.MainScreen
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.NavBarScreen
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
import com.lifting.app.feature.workout.navigation.workoutScreen
import com.lifting.app.feature.workout_detail.navigation.navigateToWorkoutDetail
import com.lifting.app.feature.workout_detail.navigation.workoutDetailScreen
import com.lifting.app.feature.workout_edit.navigation.navigateToWorkoutEdit
import com.lifting.app.feature.workout_edit.navigation.workoutEditScreen
import com.lifting.app.feature.workout_template_preview.navigation.navigateToWorkoutTemplatePreview
import com.lifting.app.feature.workout_template_preview.navigation.workoutTemplatePreviewScreen

/**
 * Created by bedirhansaricayir on 03.08.2024
 */

internal const val animDuration = 250

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LiftingNavHost() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    MainScreen(
        modifier = Modifier,
        navController = navController,
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        MainScreenScaffold(
            panelHidden = true,
            swipeableState = rememberSwipeableState(initialValue = 0),
            bottomBar = {
                LiftingBottomBar(navController = navController)
            },
            panel = { Text(text = "Workout \n Panel \n Görünümü") },
            panelTopCommon = {  },
            panelTopCollapsed = { Text(text = "Panel \n" +
                    " Top \n" +
                    " Collapsed")},
            panelTopExpanded = { Text(text = "Panel \n Top \n Expanded") })
        {
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
            navController = navController,
            onNavigateBack = navController::popBackStack
        )
        workoutDetailScreen(
            onNavigateBack = navController::popBackStack,
            onNavigateToWorkoutEdit = navController::navigateToWorkoutEdit
        )
        workoutEditScreen(
            navController = navController,
            navigateToExercises = navController::navigateToExercisesBottomSheet,
            navigateToBack = navController::popBackStack
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
            navController = navController,
            navigateToExercises = navController::navigateToExercisesBottomSheet,
            navigateToBack = navController::popBackStack
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
        exerciseDetailScreen()
        createExercisesBottomSheet(navController)
        exercisesCategoryBottomSheet(navController)
        exercisesMuscleBottomSheet(navController)
    }
}

private fun NavGraphBuilder.settingsRoot(navController: NavController) {
    navigation<NavBarScreen.Settings>(
        startDestination = LiftingScreen.Settings
    ) {
        settings(navController)
    }
}

private fun NavGraphBuilder.dashboard(navController: NavController) {
    composable<LiftingScreen.Dashboard> {
        DashboardScreen()
    }

}

@Composable
private fun DashboardScreen() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Body")

    }
}


private fun NavGraphBuilder.settings(navController: NavController) {
    composable<LiftingScreen.Settings> {
        SampleScreen("settings") {
            navController.navigate(LiftingScreen.Measure)
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

@Composable
private fun SampleScreen(text: String, onClick: () -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, modifier = Modifier.clickable { onClick() })
    }
}