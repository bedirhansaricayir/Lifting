package com.lifting.app.feature.exercise_detail

import com.lifting.app.core.base.viewmodel.State

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

sealed interface ExerciseDetailUIState : State {
    data object Loading : ExerciseDetailUIState
    data object Success : ExerciseDetailUIState
    data object Error : ExerciseDetailUIState
}