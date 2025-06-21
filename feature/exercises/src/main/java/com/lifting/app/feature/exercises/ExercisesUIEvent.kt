package com.lifting.app.feature.exercises

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.Equipment

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

internal sealed interface ExercisesUIEvent : Event {
    data object OnAddClick : ExercisesUIEvent
    data class OnSearchQueryChanged(val query: String) : ExercisesUIEvent
    data class OnExerciseClick(val id: String, val isBottomSheet: Boolean) : ExercisesUIEvent
    data object OnCategoryChipClick : ExercisesUIEvent
    data object OnEquipmentChipClick : ExercisesUIEvent
    data object OnDismissCategoryDropDown : ExercisesUIEvent
    data object OnDismissEquipmentDropDown : ExercisesUIEvent
    data class OnCategoryFilterClick(val muscle: String) : ExercisesUIEvent
    data class OnEquipmentFilterClick(val equipment: Equipment) : ExercisesUIEvent
    data class OnRemoveFiltersClick(val filterType: ExercisesFilterType) : ExercisesUIEvent
}