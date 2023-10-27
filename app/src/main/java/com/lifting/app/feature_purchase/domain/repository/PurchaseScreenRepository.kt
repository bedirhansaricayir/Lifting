package com.lifting.app.feature_purchase.domain.repository

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_purchase.domain.model.PurchaseScreenData

interface PurchaseScreenRepository {

    suspend fun getPurchaseScreenData(): Resource<PurchaseScreenData>

}