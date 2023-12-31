package com.lifting.app.feature_purchase.data.repository

import com.lifting.app.R
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_purchase.domain.model.ProductCardData
import com.lifting.app.feature_purchase.domain.model.ProvidedFeaturesData
import com.lifting.app.feature_purchase.domain.model.PurchaseScreenData
import com.lifting.app.feature_purchase.domain.repository.PurchaseScreenRepository
import javax.inject.Inject

class PurchaseScreenRepositoryImpl @Inject constructor(

): PurchaseScreenRepository {
    private val productCardData by lazy {
        listOf(
            ProductCardData(
                title = R.string.label_4_week_purchase,
                subTitle = R.string.label_4_week_purchase_payment
            ),
            ProductCardData(
                title = R.string.label_8_week_purchase,
                subTitle = R.string.label_8_week_purchase_payment
            ),
            ProductCardData(
                title = R.string.label_12_week_purchase,
                subTitle = R.string.label_12_week_purchase_payment
            )

        )
    }
    private val providedFeaturesData by lazy {
        listOf(
            ProvidedFeaturesData(
                title = R.string.label_personalized_program_purchase
            ),
            ProvidedFeaturesData(
                title = R.string.label_detailed_graphic_analysis_purchase
            ),
            ProvidedFeaturesData(
                title = R.string.label_custom_notifications_purchase
            )
        )
    }


    override suspend fun getPurchaseScreenData(): Resource<PurchaseScreenData> {
        return Resource.Success(PurchaseScreenData(productCardData,providedFeaturesData))
    }

}