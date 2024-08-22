package com.lifting.app.feature.create_exercise.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.create_exercise.CreateExerciseScreen

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

val CREATE_EXERCISE_SCREEN = LiftingScreen.CreateExercisesBottomSheet().route

fun NavController.navigateToCreateExercise() = navigate(CREATE_EXERCISE_SCREEN)

fun NavGraphBuilder.createExerciseBottomSheetScreen(
    onNavigateBack: () -> Unit
) {
    bottomSheet(CREATE_EXERCISE_SCREEN) {
        CreateExerciseScreen(
            onNavigateBack = onNavigateBack
        )
    }
}