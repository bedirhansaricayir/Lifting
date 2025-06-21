package com.lifting.app.core.data.repository.barbells

import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.BarbellsDao
import com.lifting.app.core.model.Barbell
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

class BarbellsRepositoryImpl @Inject constructor(
    private val barbellsDao: BarbellsDao
): BarbellsRepository {
    override fun getBarbells(): Flow<List<Barbell>> =
        barbellsDao.getBarbells().map { it.toDomain() }

    override fun getActiveBarbells(): Flow<List<Barbell>> =
        barbellsDao.getActiveBarbells().map { it.toDomain() }

    override fun getBarbell(barbellId: String): Flow<Barbell> =
        barbellsDao.getBarbell(barbellId).map { it.toDomain() }

    override suspend fun upsertBarbell(barbell: Barbell) =
        barbellsDao.insertBarbell(barbell.toEntity())

    override suspend fun updateIsActive(barbellId: String, isActive: Boolean) =
        barbellsDao.updateIsActive(barbellId, isActive)

    override suspend fun deleteBarbell(barbellId: String) =
        barbellsDao.deleteBarbell(barbellId)
}