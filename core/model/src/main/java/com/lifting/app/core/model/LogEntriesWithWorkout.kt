package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

data class LogEntriesWithWorkout(
    val junction: ExerciseWorkoutJunc,
    val workout: Workout,
    val logEntries: List<ExerciseLogEntry>,
    val notes: List<ExerciseSetGroupNote>?
)