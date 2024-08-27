package com.lifting.app.feature.exercises.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.exercises.ExercisesScreen
import com.lifting.app.feature.exercises.ExercisesUIEffect
import com.lifting.app.feature.exercises.ExercisesViewModel

/**
 * Created by bedirhansaricayir on 29.07.2024
 */

val EXERCISES_SCREEN = LiftingScreen.Exercises

fun NavController.navigateToExercises() = navigate(EXERCISES_SCREEN)

fun NavGraphBuilder.exercisesScreen(
    onAddClick: () -> Unit
) {
    composable<LiftingScreen.Exercises> {

        val viewModel: ExercisesViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect
        val results by viewModel.exercises.collectAsStateWithLifecycle()

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    ExercisesUIEffect.NavigateToAddExercise -> onAddClick()
                }
            }
        }

        ExercisesScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}