package com.lifting.app.feature_home.data.repository

import com.lifting.app.feature_home.data.remote.model.AntrenmanProgramlari
import com.lifting.app.feature_home.data.remote.ApiClient
import com.lifting.app.feature_home.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
): Repository {
    override suspend fun getProgramData(): AntrenmanProgramlari = apiClient.getProgramData()
}