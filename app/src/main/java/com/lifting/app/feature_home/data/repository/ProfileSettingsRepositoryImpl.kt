package com.lifting.app.feature_home.data.repository

import com.lifting.app.R
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.repository.ProfileSettingsData
import com.lifting.app.feature_home.domain.repository.ProfileSettingsRepository
import javax.inject.Inject

class ProfileSettingsRepositoryImpl @Inject constructor(
) : ProfileSettingsRepository {
    private val settings by lazy {
        listOf(
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_person_24,
                leadingIconContentDescription = "Notification Settings",
                itemTitle = R.string.account_information,
                trailingIconContentDescription = "Go To Notification"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_notifications_none_24,
                leadingIconContentDescription = "Notification Settings",
                itemTitle = R.string.notification_settings,
                trailingIconContentDescription = "Go To Notification"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_lock_24,
                leadingIconContentDescription = "Notification Settings",
                itemTitle = R.string.privacy_policy,
                trailingIconContentDescription = "Go To Notification"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_comment_24,
                leadingIconContentDescription = "Notification Settings",
                itemTitle = R.string.send_feedback,
                trailingIconContentDescription = "Go To Notification"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_logout_24,
                leadingIconContentDescription = "Notification Settings",
                itemTitle = R.string.logout,
                trailingIconContentDescription = "Go To Notification"
            )
        )
    }
    override suspend fun getSettings(): Resource<List<ProfileSettingsData>> {
        return Resource.Success(settings)
    }
}