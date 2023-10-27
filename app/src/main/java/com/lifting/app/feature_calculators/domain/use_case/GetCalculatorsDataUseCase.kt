package com.lifting.app.feature_calculators.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_calculators.data.repository.CalculatorsRepositoryImpl
import com.lifting.app.feature_calculators.domain.model.GridItemData
import javax.inject.Inject

class GetCalculatorsDataUseCase @Inject constructor(
    private val calculatorsRepositoryImpl: CalculatorsRepositoryImpl
){
    suspend operator fun invoke(): Resource<List<GridItemData>> = calculatorsRepositoryImpl.getCalculators()
}