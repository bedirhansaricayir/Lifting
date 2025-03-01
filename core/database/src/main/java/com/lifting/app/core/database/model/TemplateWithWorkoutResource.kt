package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.lifting.app.core.model.TemplateWithWorkout

/**
 * Created by bedirhansaricayir on 07.02.2025
 */

data class TemplateWithWorkoutResource(
    @Embedded
    var template: WorkoutTemplateEntity,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "id",
    )
    var workout: WorkoutEntity,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "workout_id"
    )
    var exerciseWorkoutJunctions: List<ExerciseWorkoutJunction>
)

fun TemplateWithWorkoutResource.toDomain() = with(this) {
    TemplateWithWorkout(
        template = template.toDomain(),
        workout = workout.toDomain(),
        junctions = exerciseWorkoutJunctions.map { it.toDomain() }

    )
}