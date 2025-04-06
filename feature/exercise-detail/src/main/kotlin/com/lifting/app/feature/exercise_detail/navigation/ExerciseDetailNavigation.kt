package com.lifting.app.feature.exercise_detail.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.exercise_detail.ExerciseDetailScreen
import com.lifting.app.feature.exercise_detail.ExerciseDetailViewModel

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

fun NavController.navigateToExerciseDetail(exerciseId: String) =
    navigate(LiftingScreen.ExerciseDetail(exerciseIdKey = exerciseId))

fun NavGraphBuilder.exerciseDetailScreen(
){
    composable<LiftingScreen.ExerciseDetail> {
        val viewModel: ExerciseDetailViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(key1 = effect) {
            effect.collect { effect ->
                when (effect) {
                    else -> {}
                }
            }
        }

        ExerciseDetailScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}