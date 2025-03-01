package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Entity(tableName = "barbells")
data class BarbellEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "weight_kg")
    var weightKg: Double? = null,

    @ColumnInfo(name = "weight_lbs")
    var weightLbs: Double? = null,

    @ColumnInfo(name = "is_active")
    var isActive: Boolean? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
)
