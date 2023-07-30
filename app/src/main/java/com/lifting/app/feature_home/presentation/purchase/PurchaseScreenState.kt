package com.lifting.app.feature_home.presentation.purchase

import com.lifting.app.feature_home.domain.repository.ProductCardData
import com.lifting.app.feature_home.domain.repository.ProvidedFeaturesData

data class PurchaseScreenState(
    val productCardData: List<ProductCardData> = emptyList(),
    val providedFeaturesData: List<ProvidedFeaturesData> = emptyList()
)
