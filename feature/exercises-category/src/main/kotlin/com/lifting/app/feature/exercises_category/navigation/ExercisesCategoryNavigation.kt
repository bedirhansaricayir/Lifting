package com.lifting.app.feature.exercises_category.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_EXERCISE_CATEGORY
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.exercises_category.ExerciseCategoryScreen
import com.lifting.app.feature.exercises_category.ExercisesCategoryUIEffect
import com.lifting.app.feature.exercises_category.ExercisesCategoryUIEvent
import com.lifting.app.feature.exercises_category.ExercisesCategoryViewModel

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

fun NavController.navigateToExercisesCategory() =
    navigate(LiftingScreen.ExercisesCategoryBottomSheet().route)

fun NavGraphBuilder.exercisesCategoryBottomSheetScreen(
    navController: NavController
) {
    bottomSheet(LiftingScreen.ExercisesCategoryBottomSheet().route) {
        val viewModel: ExercisesCategoryViewModel = hiltViewModel()
        val selectedCategory = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            SELECTED_EXERCISE_CATEGORY
        )

        LaunchedEffect(selectedCategory) {
            selectedCategory?.let {
                viewModel.setEvent(
                    ExercisesCategoryUIEvent.OnSelectedCategoryChanged(
                        selectedCategory
                    )
                )
            }
        }

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    is ExercisesCategoryUIEffect.SetCategoryToBackStack -> {

                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_EXERCISE_CATEGORY,
                            effect.selectedCategory.tag
                        )
                        navController.popBackStack()

                    }
                }
            }
        ) { state ->
            ExerciseCategoryScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}