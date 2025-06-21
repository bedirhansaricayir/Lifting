package com.lifting.app.feature.workout_detail

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout
import com.lifting.app.core.navigation.screens.LiftingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 06.04.2025
 */
@HiltViewModel
internal class WorkoutDetailViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WorkoutDetailUIState, WorkoutDetailUIEvent, WorkoutDetailUIEffect>() {
    private val workoutId = savedStateHandle.toRoute<LiftingScreen.WorkoutDetail>().workoutIdKey

    override fun setInitialState(): WorkoutDetailUIState = WorkoutDetailUIState()

    override fun handleEvents(event: WorkoutDetailUIEvent) {
        when (event) {
            is WorkoutDetailUIEvent.OnEditClicked -> navigateToWorkoutEdit()
            WorkoutDetailUIEvent.OnDeleteClicked -> deleteWorkoutAndNavigateBack()
            WorkoutDetailUIEvent.OnBackClicked -> navigateBack()
            WorkoutDetailUIEvent.OnReplayClicked -> startWorkout(false)
            WorkoutDetailUIEvent.OnDialogConfirmClicked -> setDialogThenStartWorkout()
            WorkoutDetailUIEvent.OnDialogDismissClicked -> setShowActiveWorkoutDialog(false)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        val logEntriesWithExerciseFlow: Flow<List<LogEntriesWithExercise>> =
            workoutsRepository.getLogEntriesWithExercise(workoutId)

        val workoutFlow: Flow<Workout> = workoutsRepository.getWorkout(workoutId)

        val totalVolumeFlow: Flow<Double> =
            workoutsRepository.getTotalVolumeLiftedByWorkoutId(workoutId)

        combine(
            logEntriesWithExerciseFlow,
            workoutFlow,
            totalVolumeFlow
        ) { logEntriesWithExercise, workout, totalVolume ->
            updateState { currentState ->
                currentState.copy(
                    workout = workout,
                    logs = logEntriesWithExercise,
                    volume = totalVolume,
                    pr = calculatePRs(workout, logEntriesWithExercise)
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun calculatePRs(
        workout: Workout,
        entries: List<LogEntriesWithExercise>
    ): Int {
        var prs = 0

        prs += workout.personalRecords?.size ?: 0
        prs += entries.flatMap { it.logEntries }.fastSumBy { it.personalRecords?.size ?: 0 }

        return prs
    }

    private fun navigateToWorkoutEdit() {
        setEffect(WorkoutDetailUIEffect.NavigateToWorkoutEdit(workoutId))
    }

    private fun deleteWorkoutAndNavigateBack() {
        viewModelScope.launch {
            workoutsRepository.deleteWorkoutWithAllDependencies(workoutId)
            navigateBack()
        }
    }

    private fun startWorkout(discardActive: Boolean) {
        viewModelScope.launch {
            workoutsRepository.startWorkoutFromWorkout(
                workoutId = workoutId,
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

    private fun navigateBack() = setEffect(WorkoutDetailUIEffect.NavigateBack)

}