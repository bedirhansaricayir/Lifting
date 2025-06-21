package com.lifting.app.feature.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.model.Equipment
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.Muscle
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.core.ui.extensions.toLocalizedMuscleName
import com.lifting.app.core.ui.extensions.toLocalizedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

@HiltViewModel
internal class ExercisesViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ExercisesUIState, ExercisesUIEvent, ExercisesUIEffect>() {

    private val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    private val selectedCategoryFilters = MutableStateFlow<List<String>>(emptyList())
    private val selectedEquipmentFilters = MutableStateFlow<List<Equipment>>(emptyList())

    override fun setInitialState(): ExercisesUIState = ExercisesUIState()

    override fun handleEvents(event: ExercisesUIEvent) {
        when (event) {
            ExercisesUIEvent.OnAddClick -> setEffect(ExercisesUIEffect.NavigateToAddExercise)
            is ExercisesUIEvent.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is ExercisesUIEvent.OnExerciseClick -> exerciseClicked(event.id, event.isBottomSheet)
            ExercisesUIEvent.OnCategoryChipClick -> updateSelectedFilterType(ExercisesFilterType.CATEGORY)
            is ExercisesUIEvent.OnCategoryFilterClick -> toggleCategoryFilter(event.muscle)
            ExercisesUIEvent.OnDismissCategoryDropDown -> updateSelectedFilterType()
            ExercisesUIEvent.OnDismissEquipmentDropDown -> updateSelectedFilterType()
            ExercisesUIEvent.OnEquipmentChipClick -> updateSelectedFilterType(ExercisesFilterType.EQUIPMENT)
            is ExercisesUIEvent.OnEquipmentFilterClick -> toggleEquipmentFilter(event.equipment)
            is ExercisesUIEvent.OnRemoveFiltersClick -> updateFilters(event.filterType)
        }
    }

    init {
        observeUiState()
    }

    private fun observeUiState() {
        searchQuery
            .debounce(DEBOUNCE_MS)
            .map { query -> query.trim() }
            .distinctUntilChanged()
            .observeExercisesByQuery()
            .groupByFirstLetter()
            .filterByEquipmentAndMuscles()
            .onEach { groupedExercises ->
                updateState { currentState ->
                    currentState.copy(
                        groupedExercises = groupedExercises,
                        searchQuery = searchQuery.value,
                        categoryFilterItems = Muscle.entries.map { category ->
                            Selectable(
                                item = category.tag,
                                selected = selectedCategoryFilters.value.contains(category.tag)
                            )
                        },
                        hasActiveMuscleFilters = selectedCategoryFilters.value.isNotEmpty(),
                        equipmentFilterItems = Equipment.entries.map {
                            Selectable(
                                item = it,
                                selected = selectedEquipmentFilters.value.contains(it)
                            )
                        },
                        hasActiveEquipmentFilters = selectedEquipmentFilters.value.isNotEmpty(),
                        categoryFilterTitle = deriveCategoriesToText(),
                        equipmentFilterTitle = deriveEquipmentToText(),
                    )
                }
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun deriveCategoriesToText(): UiText {
        val categories = selectedCategoryFilters.value.map { it.toLocalizedMuscleName() }
        return when (categories.size) {
            0 -> UiText.StringResource(com.lifting.app.core.ui.R.string.all_category_title)
            1 -> UiText.StringResource(categories.first())
            2 -> UiText.Combined(
                format = "%s, %s",
                uiTexts = arrayOf(
                    UiText.StringResource(categories.first()),
                    UiText.StringResource(categories.last())
                )
            )

            else -> {
                val extraElementCount = categories.size - 2
                UiText.Combined(
                    format = "%s, %s +$extraElementCount",
                    uiTexts = arrayOf(
                        UiText.StringResource(categories.first()),
                        UiText.StringResource(categories[1])
                    )
                )
            }
        }
    }

    private fun deriveEquipmentToText(): UiText {
        val equipments = selectedEquipmentFilters.value.map { it.toLocalizedString() }
        return when (equipments.size) {
            0 -> UiText.StringResource(com.lifting.app.core.ui.R.string.all_equipment_title)
            1 -> UiText.StringResource(equipments.first())
            2 -> UiText.Combined(
                format = "%s, %s",
                uiTexts = arrayOf(
                    UiText.StringResource(equipments.first()),
                    UiText.StringResource(equipments.last())
                )
            )

            else -> {
                val extraElementCount = equipments.size - 2
                UiText.Combined(
                    format = "%s, %s +$extraElementCount",
                    uiTexts = arrayOf(
                        UiText.StringResource(equipments.first()),
                        UiText.StringResource(equipments[1])
                    )
                )
            }
        }
    }

    private fun Flow<String>.observeExercisesByQuery(): Flow<List<ExerciseWithInfo>> {
        return this.flatMapLatest { query ->
            exercisesRepository.getAllExerciseWithInfo(searchQuery = query.takeIf { it.isNotBlank() })
        }
    }

    private fun Flow<List<ExerciseWithInfo>>.groupByFirstLetter(): Flow<Map<String, List<ExerciseWithInfo>>> {
        return this.map { exercises ->
            exercises.groupBy { it.exercise.name?.first()?.uppercase().toString() }
        }
    }

    private fun Flow<Map<String, List<ExerciseWithInfo>>>.filterByEquipmentAndMuscles(): Flow<Map<String, List<ExerciseWithInfo>>> {
        return combine(
            this,
            selectedEquipmentFilters,
            selectedCategoryFilters
        ) { groupedExercises, equipments, muscles ->
            groupedExercises.mapNotNull { (groupKey, exercises) ->
                val filtered = exercises.filter { exercise ->
                    val matchesEquipmentFilter = equipments
                        .takeIf { it.isNotEmpty() }
                        ?.any { it.id == exercise.exercise.equipmentId }
                        ?: true

                    val matchesMuscleFilter = muscles
                        .takeIf { it.isNotEmpty() }
                        ?.any { it == exercise.exercise.primaryMuscleTag }
                        ?: true

                    matchesEquipmentFilter && matchesMuscleFilter
                }
                if (filtered.isNotEmpty()) groupKey to filtered else null
            }.toMap()
        }

    }


    private fun onSearchQueryChanged(query: String) {
        setSavedStateHandle(query)
        updateState { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    private fun exerciseClicked(id: String, isBottomSheet: Boolean) {
        if (isBottomSheet) setEffect(ExercisesUIEffect.SetExerciseToBackStack(id))
        else setEffect(ExercisesUIEffect.NavigateToDetail(id))
    }

    private fun toggleCategoryFilter(muscle: String) {
        selectedCategoryFilters.update { selectedMuscles ->
            if (muscle in selectedMuscles) {
                selectedMuscles - muscle
            } else {
                selectedMuscles + muscle
            }
        }
    }

    private fun toggleEquipmentFilter(equipment: Equipment) {
        selectedEquipmentFilters.update { selectedEquipment ->
            if (equipment in selectedEquipment) {
                selectedEquipment - equipment
            } else {
                selectedEquipment + equipment
            }
        }
    }

    private fun updateFilters(filterType: ExercisesFilterType) {
        when (filterType) {
            ExercisesFilterType.EQUIPMENT -> selectedEquipmentFilters.update { emptyList() }
            ExercisesFilterType.CATEGORY -> selectedCategoryFilters.update { emptyList() }
        }
    }

    private fun updateSelectedFilterType(filterType: ExercisesFilterType? = null) {
        updateState { currentState ->
            currentState.copy(
                selectedFilterType = filterType
            )
        }
    }

    private fun setSavedStateHandle(query: String = "") {
        savedStateHandle[SEARCH_QUERY] = query
    }

}

private const val DEBOUNCE_MS = 100L
private const val SEARCH_QUERY = "searchQuery"