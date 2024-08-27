package com.lifting.app.feature.create_exercise

import com.lifting.app.core.base.viewmodel.Effect

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
sealed interface CreateExerciseUIEffect : Effect {
    data object NavigateBack : CreateExerciseUIEffect
    data class NavigateToCategories(val selectedCategory: String) : CreateExerciseUIEffect
}