package com.lifting.app.feature_home.data.local.repository

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

interface AnalysisRepository {

    suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity)

    fun getAnalysisDataWhereTimeRange(currentDate: LocalDate, endDate: LocalDate): Flow<List<ChartState>>

}