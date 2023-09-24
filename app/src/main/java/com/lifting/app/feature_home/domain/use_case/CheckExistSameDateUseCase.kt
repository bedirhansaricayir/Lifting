package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import java.time.LocalDate
import javax.inject.Inject

class CheckExistSameDateUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
     operator fun invoke(selectedDate: LocalDate): AnalysisDataEntity? {
       return analysisRepository.checkExistLocalDate(selectedDate)
    }
}