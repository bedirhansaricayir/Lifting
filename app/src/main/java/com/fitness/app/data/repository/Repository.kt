package com.fitness.app.data.repository

import com.fitness.app.data.remote.model.AntrenmanProgramlari

interface Repository {
    suspend fun getProgramData(): AntrenmanProgramlari
}