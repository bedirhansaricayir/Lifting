package com.lifting.app.feature_home.domain.repository

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lifting.app.common.util.Resource

data class ProfileSettingsData(
    @DrawableRes val leadingIcon: Int,
    val leadingIconContentDescription: String,
    @StringRes val itemTitle: Int,
    val trailingIconContentDescription: String
)
interface ProfileSettingsRepository {

    suspend fun getSettings(): Resource<List<ProfileSettingsData>>
}