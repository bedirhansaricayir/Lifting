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
class WorkoutDetailViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WorkoutDetailUIState, WorkoutDetailUIEvent, WorkoutDetailUIEffect>() {
    private val workoutId = savedStateHandle.toRoute<LiftingScreen.WorkoutDetail>().workoutIdKey

    override fun setInitialState(): WorkoutDetailUIState = WorkoutDetailUIState.Loading

    override fun handleEvents(event: WorkoutDetailUIEvent) {
        when (event) {
            is WorkoutDetailUIEvent.OnEditClicked -> navigateToWorkoutEdit()
            WorkoutDetailUIEvent.OnDeleteClicked -> deleteWorkoutAndNavigateBack()
            WorkoutDetailUIEvent.OnBackClicked -> navigateBack()
        }
    }

    init {
        initUIState(workoutId)
    }

    private fun initUIState(
        workoutId: String
    ) {
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
            runCatching {
                updateState { currentState ->
                    (currentState as WorkoutDetailUIState.Success).copy(
                        workout = workout,
                        logs = logEntriesWithExercise,
                        volume = totalVolume,
                        pr = calculatePRs(workout, logEntriesWithExercise)
                    )
                }
            }.getOrElse { throwable ->
                if (throwable is ClassCastException) {
                    setState(
                        WorkoutDetailUIState.Success(
                            workout = workout,
                            logs = logEntriesWithExercise,
                            volume = totalVolume,
                            pr = calculatePRs(workout, logEntriesWithExercise)
                        )
                    )
                } else {
                    setState(WorkoutDetailUIState.Error)
                }
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

    private fun navigateBack() = setEffect(WorkoutDetailUIEffect.NavigateBack)

}