package com.lifting.app.data.repository

import com.lifting.app.data.remote.model.AntrenmanProgramlari
import com.lifting.app.data.remote.ApiClient
import com.lifting.app.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
): Repository {
    override suspend fun getProgramData(): AntrenmanProgramlari = apiClient.getProgramData()
}