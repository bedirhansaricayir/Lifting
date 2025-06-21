package com.lifting.app.core.database.model

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.database.util.LocalDateTimeSerializer
import com.lifting.app.core.model.ExerciseCategory
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@SuppressLint("UnsafeOptInUsageError")
@Entity(tableName = "exercises")
@Serializable
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "exercise_id")
    val exerciseId: String,

    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "notes")
    var notes: String? = null,

    @ColumnInfo(name = "equipment_id")
    var equipmentId: String? = null,

    @ColumnInfo(name = "primary_muscle_tag")
    var primaryMuscleTag: String? = null,
    @ColumnInfo(name = "secondary_muscle_tag")
    var secondaryMuscleTag: String? = null,
    @ColumnInfo(name = "category")
    var category: ExerciseCategory? = null,

    @ColumnInfo(name = "created_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    @ColumnInfo(name = "update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
)