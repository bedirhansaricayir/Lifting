package com.lifting.app.feature.create_exercise

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.ExerciseCategory

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
sealed interface CreateExerciseUIEvent : Event {
    data object OnNavigationClick : CreateExerciseUIEvent
    data object OnActionClick : CreateExerciseUIEvent
    data class OnExerciseNameChanged(val exerciseName: String) : CreateExerciseUIEvent
    data class OnExerciseNotesChanged(val exerciseNotes: String) : CreateExerciseUIEvent
    data class OnCategoryChanged(val category: ExerciseCategory) : CreateExerciseUIEvent
    data class OnSelectedMuscleChanged(val selectedMuscle: String) : CreateExerciseUIEvent
    data class OnCategoryClicked(val selectedCategory: String) : CreateExerciseUIEvent
}