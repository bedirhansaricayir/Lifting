package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

data class LogEntriesWithExtraInfo(
    val junction: ExerciseWorkoutJunc,
    val exercise: Exercise,
    val primaryMuscle: Muscle,
    val logEntries: List<ExerciseLogEntry>
)