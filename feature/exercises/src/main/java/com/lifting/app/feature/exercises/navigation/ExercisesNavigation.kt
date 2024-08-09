package com.lifting.app.feature.exercises.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.exercises.ExercisesScreen

/**
 * Created by bedirhansaricayir on 29.07.2024
 */

val EXERCISES_SCREEN = LiftingScreen.Exercises

fun NavController.navigateToExercises() = navigate(EXERCISES_SCREEN)

fun NavGraphBuilder.exercisesScreen(
    onAddClick: () -> Unit
) {
    composable<LiftingScreen.Exercises> {
        ExercisesScreen(
            onAddClick = onAddClick
        )
    }
}