package com.lifting.app.feature.workout_panel

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.getBackStackArgs
import com.lifting.app.core.common.extensions.toArrayList
import com.lifting.app.core.common.extensions.toReadableDuration
import com.lifting.app.core.common.extensions.toReadableString
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
import com.lifting.app.core.model.calculateTotalVolume
import com.lifting.app.feature.workout_editor.utils.WorkoutEditorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

@HiltViewModel
class WorkoutPanelViewModel @Inject constructor(
    private val barbellsRepository: BarbellsRepository,
    private val workoutsRepository: WorkoutsRepository
) : BaseViewModel<WorkoutPanelUIState, WorkoutPanelUIEvent, WorkoutPanelUIEffect>() {
    private val currentWorkoutId = workoutsRepository.getActiveWorkoutId()
    private var durationJob: Job? = null

    override fun setInitialState(): WorkoutPanelUIState = WorkoutPanelUIState.Loading

    override fun handleEvents(event: WorkoutPanelUIEvent) {
        when (event) {
            WorkoutPanelUIEvent.OnAddExerciseButtonClicked -> navigateToExerciseSheet()
            is WorkoutPanelUIEvent.OnAddNoteClicked -> addEmptyNote(event.logEntriesWithExercise)
            is WorkoutPanelUIEvent.OnAddSetClicked -> addSet(
                event.setNumber,
                event.exerciseWorkoutJunc
            )

            WorkoutPanelUIEvent.OnCancelWorkoutClicked -> cancelCurrentWorkout()
            is WorkoutPanelUIEvent.OnDeleteExerciseClicked -> deleteExercise(event.logEntriesWithExercise)
            is WorkoutPanelUIEvent.OnDeleteNoteClicked -> deleteNote(event.exercisesSetGroupNote)
            is WorkoutPanelUIEvent.OnLogEntryDeleted -> deleteLogEntry(event.logEntry)
            is WorkoutPanelUIEvent.OnLogEntryUpdated -> updateLogEntry(event.logEntry)
            is WorkoutPanelUIEvent.OnNoteChanged -> updateNote(event.exercisesSetGroupNote)
            is WorkoutPanelUIEvent.OnWarmUpSetsUpdated -> updateWarmUpSets(
                event.logEntriesWithExercise,
                event.sets
            )

            is WorkoutPanelUIEvent.OnWorkoutNameChanged -> updateWorkoutName(event.workoutName)
            is WorkoutPanelUIEvent.OnWorkoutNoteChanged -> updateWorkoutNote(event.workoutNote)
            WorkoutPanelUIEvent.OnFinishButtonClicked -> finishWorkout()
            is WorkoutPanelUIEvent.OnExerciseAdded -> addExercise(event.exerciseId)
            WorkoutPanelUIEvent.OnUpdateBarbellClicked -> navigateToBarbellSelectorSheet()

            is WorkoutPanelUIEvent.OnBarbellUpdated -> updateBarbellByJunctionId(event.barbellWithJunctionId)
            WorkoutPanelUIEvent.OnAddToSupersetClicked -> navigateToSupersetSelectorSheet()

            is WorkoutPanelUIEvent.OnSupersetUpdated -> updateSuperset(event.superSetResult)
            is WorkoutPanelUIEvent.OnRemoveFromSupersetClicked -> removeFromSuperSet(event.logEntriesWithExercise)
        }
    }

    init {
        viewModelScope.launch {
            currentWorkoutId.collectLatest { workoutId ->
                if (workoutId != NONE_WORKOUT_ID) initUIState(workoutId)
            }
        }
    }

    private fun initUIState(
        workoutId: String
    ) {
        val barbellsFlow: Flow<List<Barbell>> = barbellsRepository.getBarbells()
        val workoutFlow: Flow<Workout> = workoutsRepository.getWorkout(workoutId)
        val logEntriesWithExerciseFlow: Flow<List<LogEntriesWithExercise>> =
            workoutsRepository.getLogEntriesWithExercise(workoutId)

        combine(
            barbellsFlow,
            workoutFlow,
            logEntriesWithExerciseFlow,
        ) { barbells, workout, logEntriesWithExercise ->
            val allEntries = logEntriesWithExercise.flatMap { it.logEntries }

            runCatching {
                updateState { currentState ->
                    (currentState as WorkoutPanelUIState.Success).copy(
                        workout = workout,
                        logs = logEntriesWithExercise,
                        currentWorkoutId = workoutId,
                        currentVolume = allEntries.calculateTotalVolume().toReadableString(),
                        currentSets = allEntries.size.toString(),
                        barbells = barbells.filter { it.isActive == true }
                    )
                }
            }.getOrElse { throwable ->
                if (throwable is ClassCastException) {
                    setState(
                        WorkoutPanelUIState.Success(
                            workout = workout,
                            logs = logEntriesWithExercise,
                            currentWorkoutId = workoutId,
                            currentVolume = allEntries.calculateTotalVolume().toReadableString(),
                            currentSets = allEntries.size.toString(),
                            barbells = barbells.filter { it.isActive == true }
                        )
                    )
                } else {
                    setState(WorkoutPanelUIState.Error)
                }
            }

            updateDuration(workout.inProgress == true, workout.startAt)

        }.launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun updateDuration(
        inProgress: Boolean,
        startAt: LocalDateTime?,
    ) {
        durationJob?.cancel()
        if (inProgress && startAt != null)
            durationJob = flow {
                while (true) {
                    emit(startAt.toReadableDuration(useSpaces = false))
                    delay(1.seconds)
                }
            }
                .onEach { duration ->
                    if (getCurrentState() is WorkoutPanelUIState.Success)
                        updateState { currentState ->
                            (currentState as WorkoutPanelUIState.Success).copy(
                                currentDuration = duration
                            )
                        }

                }
                .launchIn(viewModelScope)
    }

    private fun updateWorkoutName(workoutName: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = (getCurrentState() as WorkoutPanelUIState.Success).workout.copy(
                    name = workoutName
                )
            )
        }
    }

    private fun updateWorkoutNote(workoutNote: String) {
        viewModelScope.launch {
            workoutsRepository.updateWorkout(
                workout = (getCurrentState() as WorkoutPanelUIState.Success).workout.copy(
                    note = workoutNote
                )
            )
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

    private fun updateNote(exerciseSetGroupNote: ExerciseSetGroupNote) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseSetGroupNote(exerciseSetGroupNote)
        }
    }

    private fun updateLogEntry(logEntry: ExerciseLogEntry) {
        viewModelScope.launch {
            workoutsRepository.updateExerciseLogEntry(entry = logEntry)
        }
    }

    private fun deleteLogEntry(logEntry: ExerciseLogEntry) {
        viewModelScope.launch {
            val entriesGroup =
                (getCurrentState() as WorkoutPanelUIState.Success).logs.first { logEntriesWithExercise ->
                    logEntriesWithExercise.logEntries.any { exerciseLogEntry ->
                        exerciseLogEntry.entryId == logEntry.entryId
                    }
                }.logEntries.sortedBy { it.setNumber }
            workoutsRepository.reorderEntriesGroupByDelete(
                entriesGroup = entriesGroup.toArrayList(),
                entryToDelete = logEntry
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

    private fun deleteExercise(logEntriesWithExercise: LogEntriesWithExercise) {
        viewModelScope.launch {
            workoutsRepository.deleteExerciseFromWorkout(
                logEntriesWithExercise = logEntriesWithExercise
            )
        }
    }

    private fun navigateToExerciseSheet() {
        setEffect(WorkoutPanelUIEffect.NavigateToExerciseSheet)
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

    private fun addExercise(exerciseId: String) {
        viewModelScope.launch {
            val workoutId = currentWorkoutId.firstOrNull() ?: return@launch
            workoutsRepository.addExerciseToWorkout(
                workoutId = workoutId,
                exerciseId = exerciseId
            )
        }
    }

    private fun navigateToBarbellSelectorSheet() =
        setEffect(WorkoutPanelUIEffect.NavigateToBarbellSelectorSheet)

    private fun navigateToSupersetSelectorSheet() =
        setEffect(WorkoutPanelUIEffect.NavigateToSupersetSelectorSheet)

    private fun updateBarbellByJunctionId(barbellWithJunctionId: String) {
        val junctionId = barbellWithJunctionId.getBackStackArgs().first()
        val barbellId = barbellWithJunctionId.getBackStackArgs().last()
        viewModelScope.launch {
            workoutsRepository.updateExerciseWorkoutJunctionBarbellId(junctionId, barbellId)
        }
    }

    private fun updateSuperset(superSetResult: String) {
        val toJunctionId = superSetResult.getBackStackArgs()[0]
        val selectedFromJunctionId = superSetResult.getBackStackArgs()[1]
        val superSetId = superSetResult.getBackStackArgs()[2].toInt()

        addToSuperSet(toJunctionId, superSetId)

        (getCurrentState() as WorkoutPanelUIState.Success).let { state ->
            if (!state.logs.any { it.junction.id == selectedFromJunctionId && it.junction.supersetId == superSetId }) {
                addToSuperSet(selectedFromJunctionId, superSetId)
            }
        }
    }

    private fun removeFromSuperSet(logEntriesWithExercise: LogEntriesWithExercise) {
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

    private fun cancelCurrentWorkout() {
        viewModelScope.launch {
            val workoutId = currentWorkoutId.firstOrNull() ?: return@launch
            workoutsRepository.deleteWorkoutWithAllDependencies(workoutId)
            workoutsRepository.setActiveWorkoutId(NONE_WORKOUT_ID)

        }
    }

    fun finishWorkout() {
        viewModelScope.launch {
            val workoutId = currentWorkoutId.firstOrNull() ?: return@launch
            if (allSetsAreComplete().not()) {
                setEffect(WorkoutPanelUIEffect.OnSetsInComplete)
                return@launch
            }
            workoutsRepository.finishWorkout(workoutId)
            durationJob?.cancel()
            workoutsRepository.setActiveWorkoutId(NONE_WORKOUT_ID)
        }
    }

    private fun allSetsAreComplete(): Boolean {
        val junctions = (getCurrentState() as WorkoutPanelUIState.Success).logs

        if (junctions.isEmpty()) return false

        junctions.forEach { junction ->
            val emptyExercise = junction.logEntries.isEmpty()
            if (emptyExercise) return false

            val isInComplete = junction.logEntries.any {
                WorkoutEditorUtils.isValidSet(it, junction.exercise.category).not()
            }

            if (isInComplete) return false

        }
        return true
    }

    override fun onCleared() {
        super.onCleared()
        durationJob?.cancel()
    }
}