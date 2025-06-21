package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 07.02.2025
 */

@Entity(tableName = "workout_templates")
data class WorkoutTemplateEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "is_hidden")
    var isHidden: Boolean? = null,

    @ColumnInfo(name = "workout_id")
    var workoutId: String? = null,

    @ColumnInfo(name = "last_performed_at")
    var lastPerformedAt: LocalDateTime? = null,
    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
)