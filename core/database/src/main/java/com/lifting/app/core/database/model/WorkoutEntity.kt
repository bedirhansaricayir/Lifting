package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.model.PersonalRecord
import com.lifting.app.core.model.Workout
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "note")
    var note: String? = null,

    @ColumnInfo(name = "in_progress")
    var inProgress: Boolean? = false,
    @ColumnInfo(name = "is_hidden")
    var isHidden: Boolean? = false,

    @ColumnInfo(name = "start_at")
    var startAt: LocalDateTime? = null,
    @ColumnInfo(name = "completed_at")
    var completedAt: LocalDateTime? = null,

    @ColumnInfo(name = "personal_records")
    var personalRecords: List<PersonalRecord>? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
) {
    fun getDuration(): Long? =
        startAt?.toEpochMillis()?.let { completedAt?.toEpochMillis()?.minus(it) }
}

fun WorkoutEntity.toDomain() = with(this) {
    Workout(
        id = id,
        name = name,
        note = note,
        inProgress = inProgress,
        isHidden = isHidden,
        startAt = startAt,
        completedAt = completedAt,
        personalRecords = personalRecords,
        createdAt = createdAt,
        updatedAt = updatedAt,
        duration = getDuration()
    )
}


