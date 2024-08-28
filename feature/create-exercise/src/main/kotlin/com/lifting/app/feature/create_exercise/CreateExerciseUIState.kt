package com.lifting.app.feature.create_exercise

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
sealed interface CreateExerciseUIState : State {
    data object Loading : CreateExerciseUIState
    data class Success(
        val exerciseName: String?,
        val exerciseNotes: String?,
        val categories: List<ExerciseCategory>,
        val selectedCategory: ExerciseCategory,
        val muscles: List<Muscle>,
        val selectedMuscle: Muscle? = null
    ) : CreateExerciseUIState

    data class Error(val message: String?) : CreateExerciseUIState
}