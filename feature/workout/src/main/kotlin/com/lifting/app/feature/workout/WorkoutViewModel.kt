package com.lifting.app.feature.workout

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutTemplateRepository: WorkoutTemplateRepository
) : BaseViewModel<WorkoutUIState, WorkoutUIEvent, WorkoutUIEffect>() {

    override fun setInitialState(): WorkoutUIState = WorkoutUIState.Loading

    override fun handleEvents(event: WorkoutUIEvent) {
        when (event) {
            WorkoutUIEvent.OnCreateTemplateClicked -> createTemplateAndNavigate()
            else -> {}
        }
    }

    init {
        setUiState()
    }

    private fun setUiState() {
        viewModelScope.launch {
            workoutTemplateRepository.getTemplates()
                .onStart { setState(WorkoutUIState.Loading) }
                .catch { setState(WorkoutUIState.Error) }
                .collect { setState(WorkoutUIState.Success(it)) }
        }
    }

    private fun createTemplateAndNavigate() {
        viewModelScope.launch {
            val workoutId = workoutTemplateRepository.createTemplate()
            navigateToWorkoutEdit(workoutId)
        }
    }

    private fun navigateToWorkoutEdit(workoutId: String) {
        setEffect(WorkoutUIEffect.NavigateToWorkoutEdit(workoutId))
    }
}