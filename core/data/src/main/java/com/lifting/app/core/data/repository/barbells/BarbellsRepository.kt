package com.lifting.app.core.data.repository.barbells

import com.lifting.app.core.model.Barbell
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

interface BarbellsRepository {
    fun getBarbells(): Flow<List<Barbell>>

    fun getActiveBarbells(): Flow<List<Barbell>>

    fun getBarbell(barbellId: String): Flow<Barbell>

    suspend fun upsertBarbell(barbell: Barbell)

    suspend fun updateIsActive(barbellId: String, isActive: Boolean)

    suspend fun deleteBarbell(barbellId: String)

}