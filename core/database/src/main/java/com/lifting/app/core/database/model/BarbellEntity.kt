package com.lifting.app.core.database.model

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.database.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@SuppressLint("UnsafeOptInUsageError")
@Entity(tableName = "barbells")
@Serializable
data class BarbellEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "weight_kg")
    var weightKg: Double? = null,

    @ColumnInfo(name = "weight_lbs")
    var weightLbs: Double? = null,

    @ColumnInfo(name = "is_active")
    var isActive: Boolean? = null,

    @ColumnInfo(name = "created_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var updatedAt: LocalDateTime? = null,
)
