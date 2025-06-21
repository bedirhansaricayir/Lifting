package com.lifting.app.feature.exercises_category

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.ExerciseCategory

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

internal sealed interface ExercisesCategoryUIEvent : Event {
    data class OnCategoryClick(val category: ExerciseCategory) : ExercisesCategoryUIEvent
    data class OnSelectedCategoryChanged(val category: String) : ExercisesCategoryUIEvent
}