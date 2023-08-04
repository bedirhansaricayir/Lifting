package com.lifting.app.feature_home.domain.mapper

import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.ChartState
import javax.inject.Inject

class ChartStateMapper @Inject constructor() {

    fun mapOnChartState(
        analysisDataEntity: AnalysisDataEntity
    ): ChartState {
        return ChartState(
            dateWithoutTime = analysisDataEntity.date,
            bodyweight = analysisDataEntity.bodyweight
        )
    }
}