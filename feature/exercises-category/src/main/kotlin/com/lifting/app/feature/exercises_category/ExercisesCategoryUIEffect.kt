package com.lifting.app.feature.exercises_category

import com.lifting.app.core.base.viewmodel.Effect
import com.lifting.app.core.model.ExerciseCategory

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

internal sealed interface ExercisesCategoryUIEffect : Effect {
    data class SetCategoryToBackStack(val selectedCategory: ExerciseCategory) : ExercisesCategoryUIEffect

}