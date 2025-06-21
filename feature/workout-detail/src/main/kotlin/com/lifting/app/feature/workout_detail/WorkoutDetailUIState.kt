package com.lifting.app.feature.workout_detail

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout

/**
 * Created by bedirhansaricayir on 06.04.2025
 */

@Immutable
internal data class WorkoutDetailUIState(
    val logs: List<LogEntriesWithExercise> = emptyList(),
    val workout: Workout? = null,
    val pr: Int = 0,
    val volume: Double? = null,
    val showActiveWorkoutDialog: Boolean = false
) : State
