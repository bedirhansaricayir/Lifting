package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {

    operator fun invoke(): Flow<List<ChartState>> {
        return analysisRepository.getAllAnalysisData().map { list ->
            list.map { analysisDataEntity ->
                ChartState(
                    dateWithoutTime = analysisDataEntity.date,
                    bodyweight = analysisDataEntity.bodyweight
                )
            }
        }
    }
}