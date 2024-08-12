package com.lifting.app.feature.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.exercises.ExercisesRepository
import com.lifting.app.core.model.ExerciseWithInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ExercisesUIState, ExercisesUIEvent, ExercisesUIEffect>() {

    private val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val exercises = searchQuery.flatMapLatest { q ->
        exercisesRepository.getAllExerciseWithInfo(
            searchQuery = q.trim().takeIf { query -> query.isNotBlank() }
        ).map { exerciseList ->
            setState(
                ExercisesUIState.Success(
                    groupedExercises = groupExercises(exerciseList),
                    searchMode = (getCurrentState() as? ExercisesUIState.Success)?.searchMode ?: false,
                    searchQuery = (getCurrentState() as? ExercisesUIState.Success)?.searchQuery ?: ""
                )
            )
        }.catch { throwable ->
            setState(ExercisesUIState.Error(throwable.message))
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = ExercisesUIState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )

    override fun setInitialState(): ExercisesUIState = ExercisesUIState.Loading

    override fun handleEvents(event: ExercisesUIEvent) {
        when (event) {
            ExercisesUIEvent.OnAddClick -> setEffect(ExercisesUIEffect.NavigateToAddExercise)
            ExercisesUIEvent.OnSearchClick -> searchClicked()
            is ExercisesUIEvent.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            ExercisesUIEvent.OnBackClick -> backClicked()
            ExercisesUIEvent.OnFilterClick -> filterClicked()
        }
    }

    private suspend fun groupExercises(exercises: List<ExerciseWithInfo>): Map<String, List<ExerciseWithInfo>> {
        return withContext(Dispatchers.IO) {
            exercises.groupBy { it.exercise.name?.first()?.uppercase().toString() }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        setSavedStateHandle(query)
        updateState { currentState ->
            (currentState as ExercisesUIState.Success).copy(searchQuery = query)
        }
    }

    private fun searchClicked() = updateState { currentState ->
        (currentState as ExercisesUIState.Success).copy(searchMode = !currentState.searchMode)
    }

    private fun backClicked() {
        setSavedStateHandle()
        updateState { currentState ->
            (currentState as ExercisesUIState.Success).copy(searchMode = false, searchQuery = "")
        }
    }

    private fun filterClicked() = updateState { currentState ->
        (currentState as ExercisesUIState.Success).copy(filterMode = !currentState.filterMode)
    }

    private fun setSavedStateHandle(query: String = "") {
        savedStateHandle[SEARCH_QUERY] = query
    }

}

private const val SEARCH_QUERY = "searchQuery"