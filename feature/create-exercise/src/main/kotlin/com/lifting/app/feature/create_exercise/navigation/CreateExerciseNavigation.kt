package com.lifting.app.feature.create_exercise.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.common.extensions.observeRouteArgument
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_EXERCISE_CATEGORY
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_EXERCISE_MUSCLE
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.create_exercise.CreateExerciseScreen
import com.lifting.app.feature.create_exercise.CreateExerciseUIEffect
import com.lifting.app.feature.create_exercise.CreateExerciseUIEvent
import com.lifting.app.feature.create_exercise.CreateExerciseViewModel

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

fun NavController.navigateToCreateExercise() =
    navigate(LiftingScreen.CreateExercisesBottomSheet().route)

fun NavGraphBuilder.createExerciseBottomSheetScreen(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToMuscles: () -> Unit,
) {
    bottomSheet(LiftingScreen.CreateExercisesBottomSheet().route) { entry ->
        val viewModel: CreateExerciseViewModel = hiltViewModel()
        val selectedCategory =
            entry.observeRouteArgument<String?>(SELECTED_EXERCISE_CATEGORY, null)
                .collectAsStateWithLifecycle()

        val selectedMuscle =
            entry.observeRouteArgument<String?>(SELECTED_EXERCISE_MUSCLE, null)
                .collectAsStateWithLifecycle()

        LaunchedEffect(selectedCategory) {
            selectedCategory.value?.let {
                viewModel.setEvent(CreateExerciseUIEvent.OnCategoryChanged(it))
            }
        }

        LaunchedEffect(selectedMuscle) {
            selectedMuscle.value?.let {
                viewModel.setEvent(CreateExerciseUIEvent.OnSelectedMuscleChanged(it))
            }
        }

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    CreateExerciseUIEffect.NavigateBack -> onNavigateBack()
                    is CreateExerciseUIEffect.NavigateToCategories -> {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            SELECTED_EXERCISE_CATEGORY,
                            value = effect.selectedCategory
                        )
                        onNavigateToCategories()
                    }

                    is CreateExerciseUIEffect.NavigateToMuscles -> {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            SELECTED_EXERCISE_MUSCLE,
                            value = effect.selectedMuscle
                        )
                        onNavigateToMuscles()
                    }
                }
            }
        ) { state ->
            CreateExerciseScreen(
                state = state,
                onEvent = viewModel::setEvent,
            )
        }
    }
}