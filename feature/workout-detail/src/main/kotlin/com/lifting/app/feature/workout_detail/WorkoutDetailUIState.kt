package com.lifting.app.feature.workout_detail

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout

/**
 * Created by bedirhansaricayir on 06.04.2025
 */
sealed interface WorkoutDetailUIState : State {
    data object Loading : WorkoutDetailUIState
    data class Success(
        val logs: List<LogEntriesWithExercise>,
        val workout: Workout,
        val pr: Int,
        val volume: Double
    ) : WorkoutDetailUIState
    data object Error : WorkoutDetailUIState
}