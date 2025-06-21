package com.lifting.app.feature.exercises.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.RESULT_EXERCISES_SCREEN_EXERCISE_ID
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.exercises.ExercisesScreen
import com.lifting.app.feature.exercises.ExercisesUIEffect
import com.lifting.app.feature.exercises.ExercisesViewModel

/**
 * Created by bedirhansaricayir on 29.07.2024
 */

fun NavController.navigateToExercises() = navigate(LiftingScreen.Exercises)
fun NavController.navigateToExercisesBottomSheet() = navigate(LiftingScreen.ExercisesBottomSheet().route)

fun NavGraphBuilder.exercisesScreen(
    navController: NavController,
    isBottomSheet: Boolean,
    onAddClick: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val content: @Composable () -> Unit = {
        val viewModel: ExercisesViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
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
        ) { state ->
            ExercisesScreen(
                state = state,
                onEvent = viewModel::setEvent,
                isBottomSheet = isBottomSheet
            )
        }
    }


    if (isBottomSheet) {
        bottomSheet(LiftingScreen.ExercisesBottomSheet().route) { content() }
    } else {
        composable<LiftingScreen.Exercises> { content() }
    }
}