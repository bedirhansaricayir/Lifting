package com.lifting.app.feature_purchase.presentation


sealed class PurchaseScreenEvent {
    object PurchaseButtonClicked : PurchaseScreenEvent()
}
