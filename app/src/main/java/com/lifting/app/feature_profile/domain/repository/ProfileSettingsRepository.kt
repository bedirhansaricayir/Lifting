package com.lifting.app.feature_profile.domain.repository

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_profile.domain.model.ProfileSettingsData


interface ProfileSettingsRepository {

    suspend fun getSettings(): Resource<List<ProfileSettingsData>>
}