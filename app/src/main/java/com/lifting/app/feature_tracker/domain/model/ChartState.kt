package com.lifting.app.feature_tracker.domain.model

import com.lifting.app.feature_tracker.data.local.entity.AnalysisDataEntity
import java.time.LocalDate

data class ChartState(
    val date: LocalDate,
    val data: Float,
    val description: String
) {
    fun toAnalysisDataEntity(): AnalysisDataEntity {
        return AnalysisDataEntity(
            date = date,
            data = data,
            desc = description
        )
    }
}
