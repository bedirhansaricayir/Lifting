package com.lifting.app.feature_purchase.domain.model

import androidx.annotation.StringRes

data class ProductCardData(
    @StringRes val title: Int,
    @StringRes val subTitle: Int,
)
data class ProvidedFeaturesData(
    @StringRes val title: Int
)
data class PurchaseScreenData(
    val productCardDataList: List<ProductCardData>,
    val providedFeaturesList: List<ProvidedFeaturesData>
)
