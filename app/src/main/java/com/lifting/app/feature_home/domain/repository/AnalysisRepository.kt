package com.lifting.app.feature_home.domain.repository

import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AnalysisRepository {

    suspend fun insertAnalysisData(chartState: ChartState)

    fun getAllAnalysisData(): Flow<List<ChartState>>
    fun getAnalysisDataWhereTimeRange(startDate: LocalDate, endDate: LocalDate): Flow<List<ChartState>>

    fun checkExistLocalDate(selectedDate: LocalDate): ChartState?
}