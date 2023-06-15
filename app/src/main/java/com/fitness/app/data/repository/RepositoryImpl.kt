package com.fitness.app.data.repository

import com.fitness.app.data.remote.AntrenmanProgramlari
import com.fitness.app.data.remote.ApiClient
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
): Repository {
    override suspend fun getProgramData(): AntrenmanProgramlari = apiClient.getProgramData()
}