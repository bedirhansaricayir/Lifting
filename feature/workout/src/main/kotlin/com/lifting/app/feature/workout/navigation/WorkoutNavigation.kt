package com.lifting.app.feature.workout.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.workout.WorkoutScreen
import com.lifting.app.feature.workout.WorkoutUIEffect
import com.lifting.app.feature.workout.WorkoutViewModel

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

val WORKOUT_SCREEN = LiftingScreen.Workout

fun NavController.navigateToWorkoutScreen() = navigate(WORKOUT_SCREEN)

private const val WORKOUT_ID_KEY = "workout_id"
private const val TEMPLATE_ID_KEY = "template_id"

fun NavGraphBuilder.workoutScreen(
    navController: NavController,
    onNavigateToWorkoutEdit: (String) -> Unit,
    onNavigateToWorkoutTemplatePreview: (String) -> Unit
) {
    composable<LiftingScreen.Workout> {

        val viewModel: WorkoutViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    is WorkoutUIEffect.NavigateToWorkoutEdit -> {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            WORKOUT_ID_KEY,
                            value = effect.workoutId
                        )
                        onNavigateToWorkoutEdit(effect.workoutId)
                    }

                    is WorkoutUIEffect.NavigateToWorkoutTemplatePreview -> {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            TEMPLATE_ID_KEY,
                            value = effect.templateId
                        )
                        onNavigateToWorkoutTemplatePreview(effect.templateId)
                    }
                }
            }
        }
        WorkoutScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }

}