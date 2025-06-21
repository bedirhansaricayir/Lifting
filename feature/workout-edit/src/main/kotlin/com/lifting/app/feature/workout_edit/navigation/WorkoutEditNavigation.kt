package com.lifting.app.feature.workout_edit.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.workout_edit.WorkoutEditScreen
import com.lifting.app.feature.workout_edit.WorkoutEditUIEffect
import com.lifting.app.feature.workout_edit.WorkoutEditViewModel

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

fun NavController.navigateToWorkoutEdit(workoutId: String, isTemplate: Boolean = false) =
    navigate(LiftingScreen.WorkoutEdit(workoutIdKey = workoutId, isTemplateKey = isTemplate))

fun NavGraphBuilder.workoutEditScreen(
    navigateToExercises: () -> Unit,
    navigateToBack: () -> Unit,
    navigateToBarbellSelector: () -> Unit,
    navigateToSupersetSelector: () -> Unit
) {
    composable<LiftingScreen.WorkoutEdit> {
        val viewModel: WorkoutEditViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    is WorkoutEditUIEffect.NavigateToExerciseSheet -> navigateToExercises()
                    WorkoutEditUIEffect.PopBackStack -> navigateToBack()
                    WorkoutEditUIEffect.NavigateToBarbellSelectorSheet -> navigateToBarbellSelector()
                    WorkoutEditUIEffect.NavigateToSupersetSelectorSheet -> navigateToSupersetSelector()

                }
            }
        ) { state ->
            WorkoutEditScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}