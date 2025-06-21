package com.lifting.app.feature.workout_edit

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@Immutable
data class WorkoutEditUIState(
    val workout: Workout? = null,
    val logEntriesWithExercise: List<LogEntriesWithExercise> = emptyList(),
    val barbells: List<Barbell> = emptyList(),
    val isTemplate: Boolean = false,
) : State
