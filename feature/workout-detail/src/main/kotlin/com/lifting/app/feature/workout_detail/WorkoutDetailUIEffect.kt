package com.lifting.app.feature.workout_detail

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 06.04.2025
 */
sealed interface WorkoutDetailUIEffect : Effect {
    data object NavigateBack : WorkoutDetailUIEffect
    data class NavigateToWorkoutEdit(val workoutId: String) : WorkoutDetailUIEffect
}