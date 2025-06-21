package com.lifting.app.core.database.model

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.database.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 28.05.2025
 */

@SuppressLint("UnsafeOptInUsageError")
@Entity(tableName = "plates")
@Serializable
data class PlateEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "weight")

    var weight: Double? = null,
    @ColumnInfo(name = "for_weight_unit")
    var forWeightUnit: String? = null,

    @ColumnInfo(name = "is_active")
    var isActive: Boolean? = null,

    @ColumnInfo(name = "color")
    var color: String? = null,
    @ColumnInfo(name = "color_value_type")
    var colorValueType: String? = null,

    @ColumnInfo(name = "height")
    var height: Float? = null,
    @ColumnInfo(name = "width")
    var width: Float? = null,

    @ColumnInfo(name = "created_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    var updatedAt: LocalDateTime? = null,
)