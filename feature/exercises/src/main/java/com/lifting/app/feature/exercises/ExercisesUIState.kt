package com.lifting.app.feature.exercises

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.ExerciseWithInfo

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

sealed interface ExercisesUIState : State {
    data object Loading : ExercisesUIState
    data class Success(
        val groupedExercises: Map<String, List<ExerciseWithInfo>>?,
        val searchMode: Boolean = false,
        val searchQuery: String = ""
    ) : ExercisesUIState

    data class Error(val message: String?) : ExercisesUIState
}