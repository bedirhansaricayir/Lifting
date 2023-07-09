package com.lifting.app.data.repository

import com.lifting.app.data.remote.model.AntrenmanProgramlari

interface Repository {
    suspend fun getProgramData(): AntrenmanProgramlari
}