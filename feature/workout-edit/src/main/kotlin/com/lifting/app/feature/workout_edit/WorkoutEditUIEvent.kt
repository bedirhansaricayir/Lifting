package com.lifting.app.feature.workout_edit

import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

sealed interface WorkoutEditUIEvent : Event {
    data class OnWorkoutNameChanged(val workoutName: String) : WorkoutEditUIEvent
    data class OnWorkoutNoteChanged(val workoutNote: String) : WorkoutEditUIEvent
    data class OnAddExerciseClicked(val exerciseId: String) : WorkoutEditUIEvent
    data class OnDeleteExerciseClicked(val logEntriesWithExercise: LogEntriesWithExercise) : WorkoutEditUIEvent
    data class OnAddSetClicked(
        val setNumber: Int,
        val exerciseWorkoutJunc: ExerciseWorkoutJunc
    ) : WorkoutEditUIEvent
    data class OnLogEntryDeleted(val logEntry: ExerciseLogEntry) : WorkoutEditUIEvent
    data class OnLogEntryUpdated(val logEntry: ExerciseLogEntry) : WorkoutEditUIEvent
    data class OnWarmUpSetsUpdated(val logEntriesWithExercise: LogEntriesWithExercise, val sets: List<ExerciseLogEntry>) : WorkoutEditUIEvent
    data class OnAddNoteClicked(val logEntriesWithExercise: LogEntriesWithExercise) : WorkoutEditUIEvent
    data class OnDeleteNoteClicked(val exercisesSetGroupNote: ExerciseSetGroupNote) : WorkoutEditUIEvent
    data class OnNoteChanged(val exercisesSetGroupNote: ExerciseSetGroupNote) : WorkoutEditUIEvent
    data object OnAddExerciseButtonClicked : WorkoutEditUIEvent
}