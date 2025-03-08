package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

data class WorkoutWithExtraInfoResource(
    @Embedded
    val workout: WorkoutEntity,
    @Relation(
        entity = ExerciseWorkoutJunction::class,
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    val junctions: List<LogEntriesWithExerciseResource>
)