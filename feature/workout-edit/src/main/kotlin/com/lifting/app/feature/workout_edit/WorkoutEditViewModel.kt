package com.lifting.app.feature.workout_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.getBackStackArgs
import com.lifting.app.core.common.extensions.toArrayList
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.repository.barbells.BarbellsRepository
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.core.model.Barbell
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
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@HiltViewModel
internal class WorkoutEditViewModel @Inject constructor(
    private val barbellsRepository: BarbellsRepository,
    private val workoutsRepository: WorkoutsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WorkoutEditUIState, WorkoutEditUIEvent, WorkoutEditUIEffect>() {
    private val workoutId = savedStateHandle.toRoute<LiftingScreen.WorkoutEdit>().workoutIdKey
    private val isTemplate = savedStateHandle.toRoute<LiftingScreen.WorkoutEdit>().isTemplateKey

    override fun setInitialState(): WorkoutEditUIState = WorkoutEditUIState()

    override fun handleEvents(event: WorkoutEditUIEvent) {
        when (event) {
            is WorkoutEditUIEvent.OnWorkoutNameChanged -> updateWorkoutName(event.workoutName)
            is WorkoutEditUIEvent.OnWorkoutNoteChanged -> updateWorkoutNote(event.workoutNote)
            is WorkoutEditUIEvent.OnExerciseAdded -> addExercise(event.exerciseId)
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
            WorkoutEditUIEvent.OnBackClicked -> popBackStack()
            WorkoutEditUIEvent.OnUpdateBarbellClicked -> navigateToBarbellSelectorSheet()
            is WorkoutEditUIEvent.OnBarbellUpdated -> updateBarbellByJunctionId(event.barbellWithJunctionId)
            WorkoutEditUIEvent.OnAddToSupersetClicked -> navigateToSuperSetSelectorSheet()
            is WorkoutEditUIEvent.OnRemoveFromSupersetClicked -> removeFromSuperset(event.logEntriesWithExercise)
            is WorkoutEditUIEvent.OnSupersetUpdated -> updateSuperSet(event.superSetResult)
        }
    }

    init {
        if (workoutId != NONE_WORKOUT_ID) {
            updateUIState(workoutId)
        }
    }

    private fun updateUIState(
        workoutId: String,
    ) {
        val barbellsFlow: Flow<List<Barbell>> = barbellsRepository.getBarbells()
        val logEntriesWithExerciseFlow: Flow<List<LogEntriesWithExercise>> =
            workoutsRepository.getLogEntriesWithExercise(workoutId)

        val workoutFlow: Flow<Workout> = workoutsRepository.getWorkout(workoutId)

        combine(
            barbellsFlow,
            logEntriesWithExerciseFlow,
            workoutFlow,
        ) { barbells, logEntriesWithExercise, workout ->
            updateState { currentState ->
                currentState.copy(
                    workout = workout,
                    logEntriesWithExercise = logEntriesWithExercise,
                    barbells = barbells.filter { it.isActive == true },
                    isTemplate = isTemplate
                )
            }
        }.launchIn(viewModelScope)
    }


    private fun updateWorkoutName(workoutName: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = getCurrentState().workout!!.copy(
                    name = workoutName
                )
            )
        }
    }

    private fun updateWorkoutNote(workoutNote: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = getCurrentState().workout!!.copy(
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
                getCurrentState().logEntriesWithExercise.filter { logEntriesWithExercise ->
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
                id = generateUUID(),
                exerciseWorkoutJunctionId = logEntriesWithExercise.junction.id,
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

    private fun updateBarbellByJunctionId(barbellWithJunctionId: String) {
        val junctionId = barbellWithJunctionId.getBackStackArgs().first()
        val barbellId = barbellWithJunctionId.getBackStackArgs().last()
        viewModelScope.launch {
            workoutsRepository.updateExerciseWorkoutJunctionBarbellId(junctionId, barbellId)
        }
    }

    private fun updateSuperSet(superSetResult: String) {
        val toJunctionId = superSetResult.getBackStackArgs()[0]
        val selectedFromJunctionId = superSetResult.getBackStackArgs()[1]
        val superSetId = superSetResult.getBackStackArgs()[2].toInt()

        addToSuperSet(toJunctionId, superSetId)

        getCurrentState().let { state ->
            if (!state.logEntriesWithExercise.any { it.junction.id == selectedFromJunctionId && it.junction.supersetId == superSetId }) {
                addToSuperSet(selectedFromJunctionId, superSetId)
            }
        }
    }

    private fun removeFromSuperset(logEntriesWithExercise: LogEntriesWithExercise) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseWorkoutJunctionSupersetId(
                logEntriesWithExercise.junction.id, null
            )
        }
    }

    private fun addToSuperSet(junctionId: String, superSetId: Int) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseWorkoutJunctionSupersetId(
                junctionId, superSetId
            )
        }
    }

    private fun navigateToExerciseSheet() = setEffect(WorkoutEditUIEffect.NavigateToExerciseSheet)

    private fun popBackStack() = setEffect(WorkoutEditUIEffect.PopBackStack)

    private fun navigateToBarbellSelectorSheet() =
        setEffect(WorkoutEditUIEffect.NavigateToBarbellSelectorSheet)

    private fun navigateToSuperSetSelectorSheet() =
        setEffect(WorkoutEditUIEffect.NavigateToSupersetSelectorSheet)

}