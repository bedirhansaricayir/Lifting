package com.lifting.app.feature.exercise_detail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.exercise_detail.ExerciseDetailScreen
import com.lifting.app.feature.exercise_detail.ExerciseDetailUIEffect
import com.lifting.app.feature.exercise_detail.ExerciseDetailViewModel

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

fun NavController.navigateToExerciseDetail(exerciseId: String) =
    navigate(LiftingScreen.ExerciseDetail(exerciseIdKey = exerciseId))

fun NavGraphBuilder.exerciseDetailScreen(
    popBackStack: () -> Unit
) {
    composable<LiftingScreen.ExerciseDetail> {
        val viewModel: ExerciseDetailViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    ExerciseDetailUIEffect.PopBackStack -> popBackStack()
                }
            }
        ) { state ->
            ExerciseDetailScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}