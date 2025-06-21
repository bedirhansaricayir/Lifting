package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

data class LogEntriesWithWorkoutResource(
    @Embedded val junction: ExerciseWorkoutJunction,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "id"
    )
    val workout: WorkoutEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "junction_id",
    )
    val logEntries: List<ExerciseLogEntryEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_workout_junction_id",
    )
    var notes: List<ExerciseSetGroupNoteEntity>? = null
)