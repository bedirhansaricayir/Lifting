package com.lifting.app.feature.workout_edit

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

sealed interface WorkoutEditUIEffect : Effect {
    data object NavigateToExerciseSheet : WorkoutEditUIEffect
}