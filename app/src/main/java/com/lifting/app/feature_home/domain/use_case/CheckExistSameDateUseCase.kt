package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.domain.repository.AnalysisRepository
import com.lifting.app.feature_home.domain.model.ChartState
import java.time.LocalDate
import javax.inject.Inject

class CheckExistSameDateUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    operator fun invoke(selectedDate: LocalDate): ChartState? =
        analysisRepository.checkExistLocalDate(selectedDate)
}