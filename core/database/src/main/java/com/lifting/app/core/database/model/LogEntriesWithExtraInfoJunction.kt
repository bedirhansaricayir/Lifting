package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

data class LogEntriesWithExtraInfoJunction(
    @Embedded val junction: ExerciseWorkoutJunction,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id",
    )
    val exercise: ExerciseEntity,
    @Embedded val primaryMuscle: MuscleEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "junction_id",
    )
    var logEntries: List<ExerciseLogEntryEntity>
)