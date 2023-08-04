package com.lifting.app.feature_home.data.local.repository

import android.util.Log
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.local.AnalysisDao
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val dao: AnalysisDao
) : AnalysisRepository {
    override suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity) =
        dao.insertAnalysisData(analysisDataEntity)


    override fun getAnalysisDataWhereTimeRange(
        currentDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ChartState>> = flow {
        dao.getAnalysisDataWhereTimeRange(currentDate, endDate).map { it.map {
            it.toChartState()
        }
        }
    }
}