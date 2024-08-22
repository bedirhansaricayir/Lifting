package com.lifting.app.core.data.mapper

import com.lifting.app.core.database.model.MuscleEntity
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
object Mapper {
    fun MuscleEntity.toDomain(): Muscle = with(this) {
        Muscle(
            tag = tag,
            name = name,
            type = type,
            isDeletable = isDeletable,
            isHidden = isHidden
        )
    }

    fun Muscle.toEntity(): MuscleEntity = with(this) {
        MuscleEntity(
            tag = tag,
            name = name,
            type = type,
            isDeletable = isDeletable,
            isHidden = isHidden
        )
    }
}