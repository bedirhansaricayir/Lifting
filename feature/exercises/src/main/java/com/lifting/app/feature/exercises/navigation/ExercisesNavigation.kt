package com.lifting.app.feature.exercises.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
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

const val RESULT_EXERCISES_SCREEN_EXERCISE_ID = "result_exercises_screen_exercise_id"

val EXERCISES_SCREEN = LiftingScreen.Exercises
val EXERCISES_SCREEN_BOTTOM_SHEET = LiftingScreen.ExercisesBottomSheet().route
fun NavController.navigateToExercises() = navigate(EXERCISES_SCREEN)
fun NavController.navigateToExercisesBottomSheet() = navigate(EXERCISES_SCREEN_BOTTOM_SHEET)

fun NavGraphBuilder.exercisesScreen(
    navController: NavController,
    isBottomSheet: Boolean,
    onAddClick: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val content: @Composable () -> Unit = {
        val viewModel: ExercisesViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect
        val results by viewModel.exercises.collectAsStateWithLifecycle()

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    ExercisesUIEffect.NavigateToAddExercise -> onAddClick()
                    is ExercisesUIEffect.NavigateToDetail -> navigateToDetail(effect.id)
                    is ExercisesUIEffect.SetExerciseToBackStack -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            RESULT_EXERCISES_SCREEN_EXERCISE_ID,
                            effect.id
                        )
                        navController.popBackStack()
                    }
                }
            }
        }

        ExercisesScreen(
            state = state,
            onEvent = viewModel::setEvent,
            isBottomSheet = isBottomSheet
        )
    }

    if (isBottomSheet) {
        bottomSheet(EXERCISES_SCREEN_BOTTOM_SHEET) { content() }
    } else {
        composable<LiftingScreen.Exercises> { content() }
    }
}