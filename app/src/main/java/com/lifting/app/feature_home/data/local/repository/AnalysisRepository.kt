package com.lifting.app.feature_home.data.local.repository

import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AnalysisRepository {

    suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity)

    fun getAnalysisDataWhereTimeRange(currentDate: LocalDate, endDate: LocalDate): Flow<List<AnalysisDataEntity>>

}