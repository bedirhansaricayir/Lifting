package com.lifting.app.feature.create_exercise.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.model.parseToExerciseCategory
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.create_exercise.CreateExerciseScreen
import com.lifting.app.feature.create_exercise.CreateExerciseUIEffect
import com.lifting.app.feature.create_exercise.CreateExerciseUIEvent
import com.lifting.app.feature.create_exercise.CreateExerciseViewModel

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

val CREATE_EXERCISE_SCREEN = LiftingScreen.CreateExercisesBottomSheet().route
const val SELECTED_EXERCISE_CATEGORY = "SELECTED_EXERCISE_CATEGORY"

fun NavController.navigateToCreateExercise() = navigate(CREATE_EXERCISE_SCREEN)

fun NavGraphBuilder.createExerciseBottomSheetScreen(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToCategories: (String) -> Unit
) {
    bottomSheet(CREATE_EXERCISE_SCREEN) {

        val viewModel: CreateExerciseViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect {
                when(it) {
                    CreateExerciseUIEffect.NavigateBack -> onNavigateBack()
                    is CreateExerciseUIEffect.NavigateToCategories -> onNavigateToCategories(it.selectedCategory)
                }
            }
        }

        val selectedCategory = navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>(SELECTED_EXERCISE_CATEGORY, null)
            ?.collectAsState()

        LaunchedEffect(selectedCategory) {
            selectedCategory?.value?.let {
                viewModel.setEvent(CreateExerciseUIEvent.OnCategoryChanged(it.parseToExerciseCategory()))
            }
        }

        CreateExerciseScreen(
            state = state,
            onEvent = viewModel::setEvent,
        )

    }
}