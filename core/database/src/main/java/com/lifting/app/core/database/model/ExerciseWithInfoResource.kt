package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class ExerciseWithInfoResource(
    @Embedded
    var exercise: ExerciseEntity,
    @ColumnInfo(name = "logs_count")
    var logsCount: Long,
)