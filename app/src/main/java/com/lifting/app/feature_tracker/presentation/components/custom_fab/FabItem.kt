package com.lifting.app.feature_tracker.presentation.components.custom_fab

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class FabItemType {
    FILTER,INSERT
}
data class MultiFabItem(
    val id: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val label: Int,
    val fabItemType: FabItemType
)