package com.lifting.app.feature.exercises_category.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.exercises_category.ExerciseCategoryScreen
import com.lifting.app.feature.exercises_category.ExercisesCategoryUIEffect
import com.lifting.app.feature.exercises_category.ExercisesCategoryUIEvent
import com.lifting.app.feature.exercises_category.ExercisesCategoryViewModel

/**
 * Created by bedirhansaricayir on 22.08.2024
 */
const val SELECTED_EXERCISE_CATEGORY = "SELECTED_EXERCISE_CATEGORY"

val EXERCISES_CATEGORY_SCREEN = LiftingScreen.ExercisesCategoryBottomSheet().route

fun NavController.navigateToExercisesCategory() = navigate(EXERCISES_CATEGORY_SCREEN)

fun NavGraphBuilder.exercisesCategoryBottomSheetScreen(
    navController: NavController
) {
    bottomSheet(EXERCISES_CATEGORY_SCREEN) {

        val viewModel: ExercisesCategoryViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect {
                when (it) {
                    is ExercisesCategoryUIEffect.SetCategoryToBackStack -> {

                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_EXERCISE_CATEGORY,
                            it.selectedCategory.tag
                        )
                        navController.popBackStack()

                    }
                }
            }
        }

        val selectedCategory = navController.previousBackStackEntry?.savedStateHandle?.get<String>(SELECTED_EXERCISE_CATEGORY)

        LaunchedEffect(selectedCategory) {
            selectedCategory?.let {
                viewModel.setEvent(ExercisesCategoryUIEvent.OnSelectedCategoryChanged(selectedCategory))
            }
        }

        ExerciseCategoryScreen(
            state = state,
            onEvent = viewModel::setEvent
        )

    }
}