package com.lifting.app.feature.workout_panel

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

sealed interface WorkoutPanelUIEvent : Event {
    data class OnWorkoutNameChanged(val workoutName: String) : WorkoutPanelUIEvent
    data class OnWorkoutNoteChanged(val workoutNote: String) : WorkoutPanelUIEvent
    data class OnExerciseAdded(val exerciseId: String) : WorkoutPanelUIEvent
    data class OnDeleteExerciseClicked(val logEntriesWithExercise: LogEntriesWithExercise) : WorkoutPanelUIEvent
    data class OnAddSetClicked(
        val setNumber: Int,
        val exerciseWorkoutJunc: ExerciseWorkoutJunc
    ) : WorkoutPanelUIEvent
    data class OnLogEntryDeleted(val logEntry: ExerciseLogEntry) : WorkoutPanelUIEvent
    data class OnLogEntryUpdated(val logEntry: ExerciseLogEntry) : WorkoutPanelUIEvent
    data class OnWarmUpSetsUpdated(val logEntriesWithExercise: LogEntriesWithExercise, val sets: List<ExerciseLogEntry>) : WorkoutPanelUIEvent
    data class OnAddNoteClicked(val logEntriesWithExercise: LogEntriesWithExercise) : WorkoutPanelUIEvent
    data class OnDeleteNoteClicked(val exercisesSetGroupNote: ExerciseSetGroupNote) : WorkoutPanelUIEvent
    data class OnNoteChanged(val exercisesSetGroupNote: ExerciseSetGroupNote) : WorkoutPanelUIEvent
    data object OnAddExerciseButtonClicked : WorkoutPanelUIEvent
    data object OnCancelWorkoutClicked : WorkoutPanelUIEvent
    data object OnFinishButtonClicked : WorkoutPanelUIEvent
    data object OnUpdateBarbellClicked : WorkoutPanelUIEvent
    data class OnBarbellUpdated(val barbellWithJunctionId: String) : WorkoutPanelUIEvent
    data object OnAddToSupersetClicked : WorkoutPanelUIEvent
    data class OnSupersetUpdated(val superSetResult: String) : WorkoutPanelUIEvent
    data class OnRemoveFromSupersetClicked(val logEntriesWithExercise: LogEntriesWithExercise) : WorkoutPanelUIEvent




}