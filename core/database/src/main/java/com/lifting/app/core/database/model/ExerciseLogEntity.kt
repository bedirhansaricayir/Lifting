package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.model.ExerciseLog
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@Entity(tableName = "exercise_logs")
data class ExerciseLogEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "workout_id")
    var workoutId: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
)

fun ExerciseLogEntity.toDomain() = with(this) {
    ExerciseLog(
        id = id,
        workoutId = workoutId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}