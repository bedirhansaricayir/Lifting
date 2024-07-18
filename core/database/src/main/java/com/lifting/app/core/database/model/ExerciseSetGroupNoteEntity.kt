package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.model.ExerciseSetGroupNote
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Entity(tableName = "exercise_set_group_notes")
data class ExerciseSetGroupNoteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "note")
    var note: String? = null,

    @ColumnInfo(name = "exercise_workout_junction_id")
    var exerciseWorkoutJunctionId: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
)

fun ExerciseSetGroupNoteEntity.toDomain() = with(this) {
    ExerciseSetGroupNote(
        id = id,
        note = note,
        exerciseWorkoutJunctionId = exerciseWorkoutJunctionId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}