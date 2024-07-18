package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@Entity(tableName = "muscles")
data class MuscleEntity(
    @PrimaryKey
    @ColumnInfo(name = "tag")
    val tag: String = "",
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "type")
    var type: String? = null,
    @ColumnInfo(name = "is_deletable")
    var isDeletable: Boolean? = null,
    @ColumnInfo(name = "is_hidden")
    var isHidden: Boolean? = null
)

fun MuscleEntity.toDomain() = with(this) {
    Muscle(
        tag = tag,
        name = name,
        type = type,
        isDeletable = isDeletable,
        isHidden = isHidden
    )
}