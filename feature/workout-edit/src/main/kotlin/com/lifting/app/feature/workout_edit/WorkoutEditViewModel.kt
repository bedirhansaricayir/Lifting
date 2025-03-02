package com.lifting.app.feature.workout_edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.toArrayList
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.Workout
import com.lifting.app.core.navigation.screens.LiftingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import kotlin.ClassCastException

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@HiltViewModel
class WorkoutEditViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<WorkoutEditUIState, WorkoutEditUIEvent, WorkoutEditUIEffect>() {
    private val workoutId = savedStateHandle.toRoute<LiftingScreen.WorkoutEdit>().workoutIdKey
    private val isTemplate = savedStateHandle.toRoute<LiftingScreen.WorkoutEdit>().isTemplateKey

    override fun setInitialState(): WorkoutEditUIState = WorkoutEditUIState.Loading

    override fun handleEvents(event: WorkoutEditUIEvent) {
        when (event) {
            is WorkoutEditUIEvent.OnWorkoutNameChanged -> updateWorkoutName(event.workoutName)
            is WorkoutEditUIEvent.OnWorkoutNoteChanged -> updateWorkoutNote(event.workoutNote)
            is WorkoutEditUIEvent.OnAddExerciseClicked -> addExercise(event.exerciseId)
            is WorkoutEditUIEvent.OnDeleteExerciseClicked -> deleteExercise(event.logEntriesWithExercise)
            is WorkoutEditUIEvent.OnAddSetClicked -> addSet(
                event.setNumber,
                event.exerciseWorkoutJunc
            )

            is WorkoutEditUIEvent.OnLogEntryDeleted -> deleteLogEntry(event.logEntry)
            is WorkoutEditUIEvent.OnLogEntryUpdated -> updateLogEntry(event.logEntry)
            is WorkoutEditUIEvent.OnWarmUpSetsUpdated -> updateWarmUpSets(
                event.logEntriesWithExercise,
                event.sets
            )

            is WorkoutEditUIEvent.OnAddNoteClicked -> addEmptyNote(event.logEntriesWithExercise)
            is WorkoutEditUIEvent.OnDeleteNoteClicked -> deleteNote(event.exercisesSetGroupNote)
            is WorkoutEditUIEvent.OnNoteChanged -> updateNote(event.exercisesSetGroupNote)
            WorkoutEditUIEvent.OnAddExerciseButtonClicked -> navigateToExerciseSheet()
        }
    }

    init {
        if (workoutId != "none") {
            workoutEditUiState(workoutId)
        }
    }

    private fun workoutEditUiState(
        workoutId: String,
    ) {
        val logEntriesWithExerciseFlow: Flow<List<LogEntriesWithExercise>> =
            workoutsRepository.getLogEntriesWithExercise(workoutId)

        val workoutFlow: Flow<Workout> = workoutsRepository.getWorkout(workoutId)

        combine(
            logEntriesWithExerciseFlow,
            workoutFlow,
        ) { logEntriesWithExercise, workout ->
            try {
                updateState { currentState ->
                    (currentState as WorkoutEditUIState.Success).copy(
                        workout = workout,
                        logEntriesWithExercise = logEntriesWithExercise,
                        isTemplate = isTemplate
                    ).also {
                        Log.d("viewModels","${it.workout?.name}")
                    }
                }
            } catch (exception: Exception) {
                if (exception is ClassCastException) {
                    setState(WorkoutEditUIState.Success(workout, logEntriesWithExercise, isTemplate))
                } else {
                    setState(WorkoutEditUIState.Error)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun updateWorkoutName(workoutName: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = (getCurrentState() as WorkoutEditUIState.Success).workout!!.copy(
                    name = workoutName
                )
            )
        }
    }

    private fun updateWorkoutNote(workoutNote: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = (getCurrentState() as WorkoutEditUIState.Success).workout!!.copy(
                    note = workoutNote
                )
            )
        }
    }

    private fun addExercise(exerciseId: String) {
        viewModelScope.launch {
            workoutsRepository.addExerciseToWorkout(
                workoutId = workoutId,
                exerciseId = exerciseId
            )
        }
    }

    private fun deleteExercise(logEntriesWithExercise: LogEntriesWithExercise) {
        viewModelScope.launch {
            workoutsRepository.deleteExerciseFromWorkout(
                logEntriesWithExercise = logEntriesWithExercise
            )
        }
    }

    private fun addSet(setNumber: Int, exerciseWorkoutJunc: ExerciseWorkoutJunc) {
        viewModelScope.launch {
            workoutsRepository.addEmptySetToExercise(
                setNumber = setNumber,
                exerciseWorkoutJunc = exerciseWorkoutJunc
            )
        }
    }

    private fun deleteLogEntry(logEntry: ExerciseLogEntry) {
        viewModelScope.launch {
            val entriesGroup =
                (getCurrentState() as WorkoutEditUIState.Success).logEntriesWithExercise.filter { logEntriesWithExercise ->
                    logEntriesWithExercise.logEntries.any { exerciseLogEntry ->
                        exerciseLogEntry.entryId == logEntry.entryId
                    }
                }.first().logEntries.sortedBy { it.setNumber }
            workoutsRepository.reorderEntriesGroupByDelete(
                entriesGroup = entriesGroup.toArrayList(),
                entryToDelete = logEntry
            )
        }
    }

    private fun updateLogEntry(logEntry: ExerciseLogEntry) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseLogEntry(entry = logEntry)
        }
    }

    private fun updateWarmUpSets(
        logEntriesWithExercise: LogEntriesWithExercise,
        sets: List<ExerciseLogEntry>
    ) {
        viewModelScope.launch {
            workoutsRepository.updateWarmUpSets(junction = logEntriesWithExercise, sets = sets)
        }
    }

    private fun addEmptyNote(logEntriesWithExercise: LogEntriesWithExercise) {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            val groupNote = ExerciseSetGroupNote(
                id = UUID.randomUUID().toString(),
                exerciseWorkoutJunctionId = logEntriesWithExercise.junction.workoutId,
                createdAt = now,
                updatedAt = now,
                note = null
            )
            workoutsRepository.addExerciseSetGroupNote(exerciseSetGroupNote = groupNote)
        }
    }

    private fun deleteNote(exerciseSetGroupNote: ExerciseSetGroupNote) {
        viewModelScope.launch {
            workoutsRepository.deleteExerciseSetGroupNote(exerciseSetGroupNote.id)
        }
    }

    private fun updateNote(exerciseSetGroupNote: ExerciseSetGroupNote) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseSetGroupNote(exerciseSetGroupNote)
        }
    }

    private fun navigateToExerciseSheet() {
        setEffect(WorkoutEditUIEffect.NavigateToExerciseSheet)
    }
}