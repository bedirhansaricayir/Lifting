package com.lifting.app.core.data.repository.plates

import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.PlatesDao
import com.lifting.app.core.model.Plate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 28.05.2025
 */
class PlatesRepositoryImpl @Inject constructor(
    private val platesDao: PlatesDao
): PlatesRepository {
    override fun getPlates(): Flow<List<Plate>> =
        platesDao.getPlates().map { it.toDomain() }

    override fun getActivePlates(forWeightUnit: String): Flow<List<Plate>> =
        platesDao.getActivePlates(forWeightUnit).map { it.toDomain() }

    override fun getPlate(plateId: String): Flow<Plate> =
        platesDao.getPlate(plateId).map { it.toDomain() }

    override suspend fun insertPlate(plate: Plate) =
        platesDao.insertPlate(plate.toEntity())

    override suspend fun updateIsActive(plateId: String, isActive: Boolean) =
        platesDao.updateIsActive(plateId, isActive)

    override suspend fun deletePlate(plateId: String) =
        platesDao.deletePlate(plateId)
}