package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.repository.PurchaseScreenData
import com.lifting.app.feature_home.domain.repository.PurchaseScreenRepository
import javax.inject.Inject

class GetProductCardDataUseCase @Inject constructor(
    private val purchaseScreenRepository: PurchaseScreenRepository
) {
    suspend operator fun invoke(): Resource<PurchaseScreenData> = purchaseScreenRepository.getPurchaseScreenData()

}