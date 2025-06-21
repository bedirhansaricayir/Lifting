package com.lifting.app.feature.workout_edit

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

internal sealed interface WorkoutEditUIEffect : Effect {
    data object NavigateToExerciseSheet : WorkoutEditUIEffect
    data object PopBackStack : WorkoutEditUIEffect
    data object NavigateToBarbellSelectorSheet : WorkoutEditUIEffect
    data object NavigateToSupersetSelectorSheet : WorkoutEditUIEffect
}