package com.lifting.app.feature_home.data.local.repository

import com.lifting.app.feature_home.data.local.AnalysisDao
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val dao: AnalysisDao
) : AnalysisRepository {
    override suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity) =
        dao.insertAnalysisData(analysisDataEntity)

    override fun getAllAnalysisData(): Flow<List<AnalysisDataEntity>> =
        dao.getAllAnalysisData()


    override fun getAnalysisDataWhereTimeRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<AnalysisDataEntity>> =
        dao.getAnalysisDataWhereTimeRange(startDate, endDate)

    override fun checkExistLocalDate(selectedDate: LocalDate): AnalysisDataEntity? = dao.checkExistLocalDate(selectedDate)

}