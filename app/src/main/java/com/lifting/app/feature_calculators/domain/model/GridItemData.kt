package com.lifting.app.feature_calculators.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class GridItemData(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val infoTitle: Int,
    @StringRes val infoDescription: Int,
    val category: CalculatorCategory
)