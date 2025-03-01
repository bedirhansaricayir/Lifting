package com.lifting.app.feature.workout

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

sealed interface WorkoutUIEffect : Effect {
    data class NavigateToWorkoutEdit(val workoutId: String) : WorkoutUIEffect
}