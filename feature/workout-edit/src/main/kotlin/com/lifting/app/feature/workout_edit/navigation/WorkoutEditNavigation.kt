package com.lifting.app.feature.workout_edit.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.common.extensions.clearRouteArgument
import com.lifting.app.core.common.extensions.observeRouteArgument
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.workout_edit.WorkoutEditScreen
import com.lifting.app.feature.workout_edit.WorkoutEditUIEffect
import com.lifting.app.feature.workout_edit.WorkoutEditUIEvent
import com.lifting.app.feature.workout_edit.WorkoutEditViewModel

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

val WORKOUT_EDIT_SCREEN = LiftingScreen.WorkoutEdit
const val RESULT_EXERCISES_SCREEN_EXERCISE_ID = "result_exercises_screen_exercise_id"
fun NavController.navigateToWorkoutEdit(workoutId: String, isTemplate: Boolean = false) =
    navigate(LiftingScreen.WorkoutEdit(workoutIdKey = workoutId, isTemplateKey = isTemplate))

fun NavGraphBuilder.workoutEditScreen(
    navController: NavController,
    navigateToExercises: () -> Unit,
) {
    composable<LiftingScreen.WorkoutEdit> {
        val viewModel: WorkoutEditViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    is WorkoutEditUIEffect.NavigateToExerciseSheet -> navigateToExercises()
                }
            }
        }

        val exerciseId = navController.observeRouteArgument(RESULT_EXERCISES_SCREEN_EXERCISE_ID)
            ?.collectAsStateWithLifecycle()

        LaunchedEffect(exerciseId?.value) {
            exerciseId?.value?.let {
                viewModel.setEvent(WorkoutEditUIEvent.OnAddExerciseClicked(it))
            }.also {
                navController.clearRouteArgument(RESULT_EXERCISES_SCREEN_EXERCISE_ID)
            }
        }

        WorkoutEditScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}