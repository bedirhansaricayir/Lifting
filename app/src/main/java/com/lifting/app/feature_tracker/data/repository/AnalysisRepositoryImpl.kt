package com.lifting.app.feature_tracker.data.repository

import com.lifting.app.feature_tracker.data.local.AnalysisDao
import com.lifting.app.feature_tracker.domain.repository.AnalysisRepository
import com.lifting.app.feature_tracker.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val dao: AnalysisDao
) : AnalysisRepository {
    override suspend fun insertAnalysisData(chartState: ChartState) =
        dao.insertAnalysisData(chartState.toAnalysisDataEntity())

    override fun getAllAnalysisData(): Flow<List<ChartState>> =
        dao.getAllAnalysisData().map { list -> list.map { it.toChartState() } }

    override fun getAnalysisDataWhereTimeRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ChartState>> =
        dao.getAnalysisDataWhereTimeRange(startDate, endDate)
            .map { list -> list.map { it.toChartState() } }

    override fun checkExistLocalDate(selectedDate: LocalDate): ChartState? =
        dao.checkExistLocalDate(selectedDate)?.toChartState()

}