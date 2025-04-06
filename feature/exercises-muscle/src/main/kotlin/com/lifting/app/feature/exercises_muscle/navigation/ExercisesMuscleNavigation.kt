package com.lifting.app.feature.exercises_muscle.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_EXERCISE_MUSCLE
import com.lifting.app.feature.exercises_muscle.ExercisesMuscleScreen
import com.lifting.app.feature.exercises_muscle.ExercisesMuscleUIEffect
import com.lifting.app.feature.exercises_muscle.ExercisesMuscleUIEvent
import com.lifting.app.feature.exercises_muscle.ExercisesMuscleViewModel

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

fun NavController.navigateToExercisesMuscle() = navigate(LiftingScreen.ExercisesMuscleBottomSheet().route)

fun NavGraphBuilder.exercisesMuscleBottomSheetScreen(
    navController: NavController
) {
    bottomSheet(LiftingScreen.ExercisesMuscleBottomSheet().route) {

        val viewModel: ExercisesMuscleViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect
        
        LaunchedEffect(effect) {
            effect.collect {
                when(it) {
                    is ExercisesMuscleUIEffect.SetMuscleToBackStack -> {

                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_EXERCISE_MUSCLE,
                            it.selectedMuscle.tag
                        )
                        navController.popBackStack()

                    }
                }
            }
        }

        val selectedMuscle = navController.previousBackStackEntry?.savedStateHandle?.get<String>(SELECTED_EXERCISE_MUSCLE)

        LaunchedEffect(selectedMuscle) {
            selectedMuscle?.let {
                viewModel.setEvent(ExercisesMuscleUIEvent.OnSelectedMuscleChanged(selectedMuscle))
            }
        }

        ExercisesMuscleScreen(
            state = state,
            onEvent = viewModel::setEvent
        )

    }
}