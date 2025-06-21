package com.lifting.app.core.data.repository.plates

import com.lifting.app.core.model.Plate
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 28.05.2025
 */

interface PlatesRepository {
    fun getPlates(): Flow<List<Plate>>

    fun getActivePlates(forWeightUnit: String): Flow<List<Plate>>

    fun getPlate(plateId: String): Flow<Plate>

    suspend fun insertPlate(plate: Plate)

    suspend fun updateIsActive(plateId: String, isActive: Boolean)

    suspend fun deletePlate(plateId: String)
}