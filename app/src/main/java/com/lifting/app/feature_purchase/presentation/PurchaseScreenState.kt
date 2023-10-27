package com.lifting.app.feature_purchase.presentation

import com.lifting.app.feature_purchase.domain.model.ProductCardData
import com.lifting.app.feature_purchase.domain.model.ProvidedFeaturesData


data class PurchaseScreenState(
    val productCardData: List<ProductCardData> = emptyList(),
    val providedFeaturesData: List<ProvidedFeaturesData> = emptyList()
)
