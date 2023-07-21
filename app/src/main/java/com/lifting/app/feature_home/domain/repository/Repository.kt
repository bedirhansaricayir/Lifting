package com.lifting.app.feature_home.domain.repository

import com.lifting.app.feature_home.data.remote.model.AntrenmanProgramlari

interface Repository {
    suspend fun getProgramData(): AntrenmanProgramlari
}