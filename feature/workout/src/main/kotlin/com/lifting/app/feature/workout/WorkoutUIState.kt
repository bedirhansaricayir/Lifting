package com.lifting.app.feature.workout

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.TemplateWithWorkout

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

sealed interface WorkoutUIState : State {
    data object Loading : WorkoutUIState
    data class Success(val templates : List<TemplateWithWorkout>) : WorkoutUIState
    data object Error : WorkoutUIState
}