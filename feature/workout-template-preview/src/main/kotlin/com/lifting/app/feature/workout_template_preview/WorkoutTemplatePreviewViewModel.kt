package com.lifting.app.feature.workout_template_preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.workout_template.WorkoutTemplateRepository
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.navigation.screens.LiftingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@HiltViewModel
internal class WorkoutTemplatePreviewViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    private val templateRepository: WorkoutTemplateRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WorkoutTemplatePreviewUIState, WorkoutTemplatePreviewUIEvent, WorkoutTemplatePreviewUIEffect>() {
    private val templateId = savedStateHandle.toRoute<LiftingScreen.WorkoutTemplatePreview>().templateIdKey

    override fun setInitialState(): WorkoutTemplatePreviewUIState = WorkoutTemplatePreviewUIState()

    override fun handleEvents(event: WorkoutTemplatePreviewUIEvent) {
        when (event) {
            is WorkoutTemplatePreviewUIEvent.OnEditClicked -> navigateToWorkoutEdit(event.workoutId)
            WorkoutTemplatePreviewUIEvent.OnDeleteClicked -> deleteTemplateThenPopBackStack()
            WorkoutTemplatePreviewUIEvent.OnBackClicked -> setPopBackStackEffect()
            WorkoutTemplatePreviewUIEvent.OnPlayClicked -> startWorkout(false)
            WorkoutTemplatePreviewUIEvent.OnDialogConfirmClicked -> setDialogThenStartWorkout()
            WorkoutTemplatePreviewUIEvent.OnDialogDismissClicked -> setShowActiveWorkoutDialog(false)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        viewModelScope.launch {
            templateRepository.getTemplate(templateId)
                .collectLatest { workoutTemplate ->
                    workoutTemplate.workoutId?.let {
                        val entriesFlow = workoutsRepository.getLogEntriesWithExtraInfo(it)
                        val workoutFlow = workoutsRepository.getWorkout(it)
                        combine(
                            entriesFlow,
                            workoutFlow
                        ) { entries, workout ->
                            updateState { currentState ->
                                currentState.copy(
                                    template = workoutTemplate,
                                    entries = entries,
                                    workout = workout
                                )
                            }
                        }.launchIn(viewModelScope)
                    }
                }
        }
    }

    private fun navigateToWorkoutEdit(workoutId: String) {
        setEffect(WorkoutTemplatePreviewUIEffect.NavigateToWorkoutEdit(workoutId))
    }

    private fun deleteTemplateThenPopBackStack() {
        deleteTemplate()
        setPopBackStackEffect()
    }

    private fun deleteTemplate() {
        viewModelScope.launch {
            templateRepository.deleteTemplate(templateId)
        }
    }

    private fun setPopBackStackEffect() {
        setEffect(WorkoutTemplatePreviewUIEffect.PopBackStack)
    }

    private fun startWorkout(discardActive: Boolean) {
        viewModelScope.launch {
            workoutsRepository.startWorkoutFromTemplate(
                templateId = templateId,
                discardActive = discardActive,
                onWorkoutAlreadyActive = { setShowActiveWorkoutDialog(true) }
            )
        }
    }

    private fun setDialogThenStartWorkout() {
        setShowActiveWorkoutDialog(false)
        startWorkout(true)
    }

    private fun setShowActiveWorkoutDialog(showDialog: Boolean) {
        updateState { currentState ->
            currentState.copy(
                showActiveWorkoutDialog = showDialog
            )
        }
    }
}