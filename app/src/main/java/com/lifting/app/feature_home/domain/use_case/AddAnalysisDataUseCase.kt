package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.data.local.repository.AnalysisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class AddAnalysisDataUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    suspend operator fun invoke(analysisDataEntity: AnalysisDataEntity)  {
        analysisRepository.insertAnalysisData(analysisDataEntity)
    }

}