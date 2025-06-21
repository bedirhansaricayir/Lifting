package com.lifting.app.feature.workout

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepository
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.datastore.PreferencesStorage
import com.lifting.app.core.model.LayoutType
import com.lifting.app.core.model.ScrollDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

@HiltViewModel
internal class WorkoutViewModel @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
    private val workoutsRepository: WorkoutsRepository,
    private val workoutTemplateRepository: WorkoutTemplateRepository
) : BaseViewModel<WorkoutUIState, WorkoutUIEvent, WorkoutUIEffect>() {

    override fun setInitialState(): WorkoutUIState = WorkoutUIState()

    override fun handleEvents(event: WorkoutUIEvent) {
        when (event) {
            WorkoutUIEvent.OnCreateTemplateClicked -> createTemplateAndNavigate()
            is WorkoutUIEvent.OnTemplateClicked -> navigateToWorkoutTemplatePreview(event.templateId)
            is WorkoutUIEvent.OnScrollDirectionChanged -> updateFabButtonVisibility(event.direction)
            is WorkoutUIEvent.OnLayoutTypeClicked -> updateLayoutType(event.layoutType)
            is WorkoutUIEvent.OnStartWorkoutClicked -> createWorkout(event.workoutName)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        val templatesFlow = workoutTemplateRepository.getTemplates()
        val activeWorkoutFlow = workoutsRepository.getActiveWorkoutId()
        val layoutTypeFlow = preferencesStorage.workoutScreenLayoutType

        combine(
            templatesFlow,
            activeWorkoutFlow,
            layoutTypeFlow
        ) { templates, activeWorkoutId, layoutType ->
            updateState { currentState ->
                currentState.copy(
                    templates = templates,
                    isExistActiveWorkout = activeWorkoutId != NONE_WORKOUT_ID,
                    layoutType = layoutType
                )
            }
        }.launchIn(viewModelScope)

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

    private fun navigateToWorkoutTemplatePreview(templateId: String) {
        setEffect(WorkoutUIEffect.NavigateToWorkoutTemplatePreview(templateId))
    }

    private fun updateFabButtonVisibility(direction: ScrollDirection) {
        updateState { currentState ->
            currentState.copy(
                fabIsVisible = when (direction) {
                    ScrollDirection.Up -> true
                    ScrollDirection.Down -> false
                }
            )
        }
    }

    private fun updateLayoutType(layoutType: LayoutType) {
        viewModelScope.launch {
            preferencesStorage.setWorkoutScreenLayoutType(layoutType)
        }
    }

    private fun createWorkout(workoutName: String) {
        viewModelScope.launch {
            val workoutId = generateUUID()
            val isCreated = workoutsRepository.createWorkout(
                workoutId = workoutId,
                workoutName = workoutName,
                discardActive = false,
                onWorkoutAlreadyActive = {
                    //setShowActiveWorkoutDialog(true)
                }
            )

            if (isCreated) {
                workoutsRepository.setActiveWorkoutId(workoutId)
            }
        }
    }
}