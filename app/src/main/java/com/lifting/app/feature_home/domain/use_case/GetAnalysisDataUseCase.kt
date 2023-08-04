package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import com.lifting.app.feature_home.domain.mapper.ChartStateMapper
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository,
    private val chartStateMapper: ChartStateMapper
) {
     suspend  operator fun invoke(currentDate: LocalDate, endDate: LocalDate): Flow<List<ChartState>> = flow {
         analysisRepository.getAnalysisDataWhereTimeRange(currentDate, endDate).map { list ->
             emit(list)
         }

         /*return analysisRepository.getAnalysisDataWhereTimeRange(currentDate, endDate).map { dataList ->
             dataList.map { entity ->
                 ChartState(entity.date,entity.bodyweight)
             }
         }*/

    }
 }
