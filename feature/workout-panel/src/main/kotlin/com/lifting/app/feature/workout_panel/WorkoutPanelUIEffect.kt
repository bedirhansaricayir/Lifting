package com.lifting.app.feature.workout_panel

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

sealed interface WorkoutPanelUIEffect : Effect {
    data object NavigateToExerciseSheet : WorkoutPanelUIEffect
    data object OnSetsInComplete : WorkoutPanelUIEffect
    data object NavigateToBarbellSelectorSheet : WorkoutPanelUIEffect
    data object NavigateToSupersetSelectorSheet : WorkoutPanelUIEffect
}