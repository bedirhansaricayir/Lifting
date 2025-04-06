package com.lifting.app.feature.workout_template_preview.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.workout_template_preview.WorkoutTemplatePreviewScreen
import com.lifting.app.feature.workout_template_preview.WorkoutTemplatePreviewUIEffect
import com.lifting.app.feature.workout_template_preview.WorkoutTemplatePreviewViewModel

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

fun NavController.navigateToWorkoutTemplatePreview(templateId: String) =
    navigate(LiftingScreen.WorkoutTemplatePreview(templateIdKey = templateId))

fun NavGraphBuilder.workoutTemplatePreviewScreen(
    onNavigateToWorkoutEdit: (String,Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    composable<LiftingScreen.WorkoutTemplatePreview> {
        val viewModel: WorkoutTemplatePreviewViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    is WorkoutTemplatePreviewUIEffect.NavigateToWorkoutEdit -> onNavigateToWorkoutEdit(effect.workoutId,true)
                    WorkoutTemplatePreviewUIEffect.PopBackStack -> popBackStack()
                }
            }
        }

        WorkoutTemplatePreviewScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}