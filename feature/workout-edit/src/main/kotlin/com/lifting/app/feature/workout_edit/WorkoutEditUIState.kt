package com.lifting.app.feature.workout_edit

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

sealed interface WorkoutEditUIState : State {
    data object Loading : WorkoutEditUIState
    data class Success(
        val workout: Workout? = null,
        val logEntriesWithExercise: List<LogEntriesWithExercise>
    ) : WorkoutEditUIState
    data object Error : WorkoutEditUIState
}