package com.lifting.app.feature_home.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.common.constants.Constants.Companion.ANALYSIS_DATA_TABLE
import com.lifting.app.common.constants.Constants.Companion.BW_COLUMN
import com.lifting.app.common.constants.Constants.Companion.DATE_COLUMN
import java.time.LocalDate

@Entity(tableName = ANALYSIS_DATA_TABLE)
data class AnalysisDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = DATE_COLUMN)val date: LocalDate,
    @ColumnInfo(name = BW_COLUMN)val bodyweight: Float?,
    @ColumnInfo(name = "cj_column")val cj: Float?,
    @ColumnInfo(name = "snatch_column")val snatch: Float?
)