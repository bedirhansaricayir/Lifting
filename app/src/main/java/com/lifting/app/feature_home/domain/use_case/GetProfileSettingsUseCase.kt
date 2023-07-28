package com.lifting.app.feature_home.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.repository.ProfileSettingsData
import com.lifting.app.feature_home.domain.repository.ProfileSettingsRepository
import javax.inject.Inject

class GetProfileSettingsUseCase @Inject constructor(
    private val profileSettingsRepository: ProfileSettingsRepository
) {

    suspend operator fun invoke(): Resource<List<ProfileSettingsData>> = profileSettingsRepository.getSettings()
}