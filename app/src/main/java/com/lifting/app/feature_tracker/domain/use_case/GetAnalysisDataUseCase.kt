package com.lifting.app.feature_tracker.domain.use_case

import com.lifting.app.feature_tracker.domain.repository.AnalysisRepository
import com.lifting.app.feature_tracker.domain.model.ChartState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository,
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<ChartState>> =
        analysisRepository.getAnalysisDataWhereTimeRange(startDate, endDate)
}
