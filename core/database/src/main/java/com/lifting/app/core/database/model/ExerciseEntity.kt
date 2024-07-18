package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseCategory
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "exercise_id")
    val exerciseId: String,

    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "notes")
    var notes: String? = null,

    @ColumnInfo(name = "primary_muscle_tag")
    var primaryMuscleTag: String? = null,
    @ColumnInfo(name = "secondary_muscle_tag")
    var secondaryMuscleTag: String? = null,
    @ColumnInfo(name = "category")
    var category: ExerciseCategory? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
)

fun ExerciseEntity.toDomain() = with(this) {
    Exercise(
        exerciseId = exerciseId,
        name = name,
        notes = notes,
        primaryMuscleTag = primaryMuscleTag,
        secondaryMuscleTag = secondaryMuscleTag,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}