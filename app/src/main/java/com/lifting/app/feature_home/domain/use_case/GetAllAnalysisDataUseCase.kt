package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.domain.repository.AnalysisRepository
import com.lifting.app.feature_home.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    operator fun invoke(): Flow<List<ChartState>> =
        analysisRepository.getAllAnalysisData()
}