package com.lifting.app.feature_tracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.common.constants.Constants.Companion.ANALYSIS_DATA_TABLE
import com.lifting.app.common.constants.Constants.Companion.DATA_COLUMN
import com.lifting.app.common.constants.Constants.Companion.DATE_COLUMN
import com.lifting.app.common.constants.Constants.Companion.DESC_COLUMN
import com.lifting.app.feature_tracker.domain.model.ChartState
import java.time.LocalDate

@Entity(tableName = ANALYSIS_DATA_TABLE)
data class AnalysisDataEntity(
    @PrimaryKey
    @ColumnInfo(name = DATE_COLUMN)val date: LocalDate,
    @ColumnInfo(name = DATA_COLUMN)val data: Float,
    @ColumnInfo(name = DESC_COLUMN)val desc: String
) {
    fun toChartState(): ChartState {
        return ChartState(
            date = date,
            data = data,
            description = desc
        )
    }
}