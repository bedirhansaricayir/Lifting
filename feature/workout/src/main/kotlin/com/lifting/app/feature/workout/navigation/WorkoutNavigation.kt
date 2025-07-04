package com.lifting.app.feature.workout.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.TEMPLATE_ID_KEY
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.WORKOUT_ID_KEY
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.workout.WorkoutScreen
import com.lifting.app.feature.workout.WorkoutUIEffect
import com.lifting.app.feature.workout.WorkoutViewModel

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

fun NavController.navigateToWorkoutScreen() = navigate(LiftingScreen.Workout)

fun NavGraphBuilder.workoutScreen(
    navController: NavController,
    onNavigateToWorkoutEdit: (String) -> Unit,
    onNavigateToWorkoutTemplatePreview: (String) -> Unit
) {
    composable<LiftingScreen.Workout> { entry ->
        val viewModel: WorkoutViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
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
        ) { state ->
            WorkoutScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}