package com.lifting.app.feature_home.domain.repository

import androidx.annotation.StringRes
import com.lifting.app.common.util.Resource


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

interface PurchaseScreenRepository {

    suspend fun getPurchaseScreenData(): Resource<PurchaseScreenData>
}