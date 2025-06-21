package com.lifting.app.feature.exercises

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.Equipment
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

@Immutable
internal data class ExercisesUIState(
    val groupedExercises: Map<String, List<ExerciseWithInfo>> = emptyMap(),
    val searchQuery: String = "",
    val useAnimatedPlaceholder: Boolean = false,
    val selectedFilterType: ExercisesFilterType? = null,
    val hasActiveMuscleFilters: Boolean = false,
    val hasActiveEquipmentFilters: Boolean = false,
    val categoryFilterItems: List<Selectable<String>> = listOf(),
    val equipmentFilterItems: List<Selectable<Equipment>> = listOf(),
    val categoryFilterTitle: UiText = UiText.StringResource(com.lifting.app.core.ui.R.string.all_category_title),
    val equipmentFilterTitle: UiText = UiText.StringResource(com.lifting.app.core.ui.R.string.all_equipment_title),
) : State


internal enum class ExercisesFilterType {
    CATEGORY, EQUIPMENT
}