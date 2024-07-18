package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.lifting.app.core.model.ExerciseWithInfo

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class ExerciseWithInfoResource(
    @Embedded
    var exercise: ExerciseEntity,
    @Relation(
        parentColumn = "primary_muscle_tag",
        entityColumn = "tag"
    )
    var primaryMuscle: MuscleEntity?,
    @ColumnInfo(name = "logs_count")
    var logsCount: Long,
)

fun ExerciseWithInfoResource.toDomain() = with(this) {
    ExerciseWithInfo(
        exercise = exercise.toDomain(),
        muscle = primaryMuscle?.toDomain(),
        logsCount = logsCount
    )
}