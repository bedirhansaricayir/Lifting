package com.lifting.app.feature_profile.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileSettingsData(
    @DrawableRes val leadingIcon: Int,
    val leadingIconContentDescription: String,
    @StringRes val itemTitle: Int,
    val trailingIconContentDescription: String
)
