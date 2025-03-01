package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

data class LogEntriesWithExerciseResource(
    @Embedded val junction: ExerciseWorkoutJunction,

    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id"
    )
    val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "junction_id",
    )
    var logEntries: List<ExerciseLogEntryEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_workout_junction_id",
    )
    var notes: List<ExerciseSetGroupNoteEntity>? = null
)