package com.lifting.app.domain.repository

import com.lifting.app.data.remote.model.AntrenmanProgramlari

interface Repository {
    suspend fun getProgramData(): AntrenmanProgramlari
}