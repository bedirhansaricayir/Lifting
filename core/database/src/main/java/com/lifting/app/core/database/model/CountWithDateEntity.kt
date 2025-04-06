package com.lifting.app.core.database.model

import androidx.room.ColumnInfo

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

data class CountWithDateEntity(
    @ColumnInfo(name = "count")
    val count: Long,
    @ColumnInfo(name = "date")
    val date: Long
)