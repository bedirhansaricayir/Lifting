package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

data class LogEntriesWithExercise(
    val junction: ExerciseWorkoutJunc,
    val exercise: Exercise,
    val logEntries: List<ExerciseLogEntry>,
    val notes: List<ExerciseSetGroupNote>?,
)