package com.lifting.app.feature.exercises_category

import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.ExerciseCategory

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

sealed interface ExercisesCategoryUIState : State {
    data object Loading : ExercisesCategoryUIState
    data class Success(val categories: List<ExerciseCategory>, val selectedCategory: String?) : ExercisesCategoryUIState
    data class Error(val message: String) : ExercisesCategoryUIState
}