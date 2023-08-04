package com.lifting.app.feature_home.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.feature_home.domain.model.ChartState
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "analysis_data_table")
data class AnalysisDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date_column")val date: LocalDate,
    @ColumnInfo(name = "bodyweight_column")val bodyweight: Float
) {
    /*fun toChartState(): List<ChartState> {
        return listOf(
            ChartState(
                dateWithoutTime = date,
                bodyweight = bodyweight
            )
        )
    }*/
    fun toChartState(): ChartState {
        return ChartState(
            dateWithoutTime = date,
            bodyweight = bodyweight
        )
    }
}