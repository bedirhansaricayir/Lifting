package com.lifting.app.feature.workout_detail.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.workout_detail.WorkoutDetailScreen
import com.lifting.app.feature.workout_detail.WorkoutDetailUIEffect
import com.lifting.app.feature.workout_detail.WorkoutDetailViewModel

/**
 * Created by bedirhansaricayir on 06.04.2025
 */

fun NavController.navigateToWorkoutDetail(workoutId: String) = navigate(LiftingScreen.WorkoutDetail(workoutId))

fun NavGraphBuilder.workoutDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWorkoutEdit: (String) -> Unit
) {
    composable<LiftingScreen.WorkoutDetail> {
        val viewModel: WorkoutDetailViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    WorkoutDetailUIEffect.NavigateBack -> onNavigateBack()
                    is WorkoutDetailUIEffect.NavigateToWorkoutEdit -> onNavigateToWorkoutEdit(effect.workoutId)
                }
            }
        }

        WorkoutDetailScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
} 