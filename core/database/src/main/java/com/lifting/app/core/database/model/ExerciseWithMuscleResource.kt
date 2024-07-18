package com.lifting.app.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.lifting.app.core.model.ExerciseWithMuscle

/**
 * Created by bedirhansaricayir on 15.07.2024
 */
data class ExerciseWithMuscleResource(
    @Embedded
    val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "primary_muscle_tag",
        entityColumn = "tag"
    )
    val primaryMuscle: MuscleEntity?,
    @Relation(
        parentColumn = "secondary_muscle_tag",
        entityColumn = "tag"
    )
    val secondaryMuscle: MuscleEntity?,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id",
        projection = ["id"]
    )
    val junctions: List<ExerciseWorkoutJunction>
)

fun ExerciseWithMuscleResource.toDomain() = with(this) {
    ExerciseWithMuscle(
        exercise = exercise.toDomain(),
        primaryMuscle = primaryMuscle?.toDomain(),
        secondaryMuscle = secondaryMuscle?.toDomain(),
        junctions = junctions.map { it.toDomain() }
    )
}