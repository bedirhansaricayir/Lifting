package com.lifting.app.feature_purchase.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_purchase.domain.model.PurchaseScreenData
import com.lifting.app.feature_purchase.domain.repository.PurchaseScreenRepository
import javax.inject.Inject

class GetProductCardDataUseCase @Inject constructor(
    private val purchaseScreenRepository: PurchaseScreenRepository
) {
    suspend operator fun invoke(): Resource<PurchaseScreenData> = purchaseScreenRepository.getPurchaseScreenData()

}