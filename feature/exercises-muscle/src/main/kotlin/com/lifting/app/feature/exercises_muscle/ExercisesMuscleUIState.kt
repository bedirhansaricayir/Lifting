package com.lifting.app.feature.exercises_muscle

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 28.08.2024
 */
sealed interface ExercisesMuscleUIState : State {
    data object Loading : ExercisesMuscleUIState
    data class Success(val muscles: List<Muscle>, val selectedMuscle: String? = "") : ExercisesMuscleUIState
    data class Error(val message: String) : ExercisesMuscleUIState
}