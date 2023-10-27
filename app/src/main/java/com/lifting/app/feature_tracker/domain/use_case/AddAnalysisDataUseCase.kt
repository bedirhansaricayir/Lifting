package com.lifting.app.feature_tracker.domain.use_case

import com.lifting.app.feature_tracker.domain.repository.AnalysisRepository
import com.lifting.app.feature_tracker.domain.model.ChartState
import javax.inject.Inject

class AddAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    suspend operator fun invoke(chartState: ChartState) =
        analysisRepository.insertAnalysisData(chartState)

}