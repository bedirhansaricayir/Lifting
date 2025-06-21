package com.lifting.app.feature.workout_panel

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

sealed interface WorkoutPanelUIState : State {
    data object Loading : WorkoutPanelUIState
    @Immutable
    data class Success(
        val workout: Workout,
        val logs: List<LogEntriesWithExercise>,
        val currentWorkoutId: String = NONE_WORKOUT_ID,
        val currentDuration: String = String.EMPTY,
        val currentVolume: String = String.EMPTY,
        val currentSets: String = String.EMPTY,
        val barbells: List<Barbell> = emptyList()
        ) : WorkoutPanelUIState
    data object Error : WorkoutPanelUIState
}