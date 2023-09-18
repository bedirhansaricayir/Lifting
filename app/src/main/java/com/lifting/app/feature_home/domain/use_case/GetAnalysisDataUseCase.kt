package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository,
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<ChartState>> {
        return analysisRepository.getAnalysisDataWhereTimeRange(startDate, endDate).map { list ->
            list.map { analysisDataEntity ->
                ChartState(
                    date = analysisDataEntity.date,
                    data = analysisDataEntity.data,
                    description = analysisDataEntity.desc
                )
            }
        }
    }
}
