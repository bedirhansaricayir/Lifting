package com.lifting.app.feature_profile.data.repository

import com.lifting.app.R
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_profile.domain.model.ProfileSettingsData
import com.lifting.app.feature_profile.domain.repository.ProfileSettingsRepository
import javax.inject.Inject

class ProfileSettingsRepositoryImpl @Inject constructor(
) : ProfileSettingsRepository {
    private val settings by lazy {
        listOf(
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_person_24,
                leadingIconContentDescription = "Account Information Icon",
                itemTitle = R.string.account_information,
                trailingIconContentDescription = "Go Icon"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_notifications_none_24,
                leadingIconContentDescription = "Notification Settings Icon",
                itemTitle = R.string.notification_settings,
                trailingIconContentDescription = "Go Icon"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_lock_24,
                leadingIconContentDescription = "Privacy Policy Icon",
                itemTitle = R.string.privacy_policy,
                trailingIconContentDescription = "Go Icon"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_comment_24,
                leadingIconContentDescription = "Send Feedback Icon",
                itemTitle = R.string.send_feedback,
                trailingIconContentDescription = "Go Icon"
            ),
            ProfileSettingsData(
                leadingIcon = R.drawable.baseline_logout_24,
                leadingIconContentDescription = "Logout Icon",
                itemTitle = R.string.logout,
                trailingIconContentDescription = "Go Icon"
            )
        )
    }
    override suspend fun getSettings(): Resource<List<ProfileSettingsData>> {
        return Resource.Success(settings)
    }
}