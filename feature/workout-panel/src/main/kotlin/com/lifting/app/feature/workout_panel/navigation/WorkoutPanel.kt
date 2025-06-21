package com.lifting.app.feature.workout_panel.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifting.app.feature.workout_panel.WorkoutPanelScreen
import com.lifting.app.feature.workout_panel.WorkoutPanelUIEffect
import com.lifting.app.feature.workout_panel.WorkoutPanelViewModel

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

@Composable
fun WorkoutPanel(
    navigateToExercises: () -> Unit,
    navigateToBarbellSelector: () -> Unit,
    navigateToSupersetSelector: () -> Unit
) {
    val viewModel: WorkoutPanelViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                WorkoutPanelUIEffect.NavigateToExerciseSheet -> navigateToExercises()
                WorkoutPanelUIEffect.OnSetsInComplete -> {
                    Toast.makeText(context, "Please complete all sets", Toast.LENGTH_SHORT).show()
                }

                WorkoutPanelUIEffect.NavigateToBarbellSelectorSheet -> navigateToBarbellSelector()
                WorkoutPanelUIEffect.NavigateToSupersetSelectorSheet -> navigateToSupersetSelector()

            }
        }
    }

    WorkoutPanelScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}