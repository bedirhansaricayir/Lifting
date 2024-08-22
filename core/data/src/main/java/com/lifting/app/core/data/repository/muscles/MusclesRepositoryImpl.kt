package com.lifting.app.core.data.repository.muscles

import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.MusclesDao
import com.lifting.app.core.model.Muscle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 21.08.2024
 */

internal class MusclesRepositoryImpl @Inject constructor(
    private val musclesDao: MusclesDao
) : MusclesRepository {

    override fun getMuscles(): Flow<List<Muscle>> {
        return musclesDao.getMuscles().map { musclesEntities ->
            musclesEntities.map { it.toDomain() }
        }.onEach {
            if (it.isEmpty())
                insertMuscle(getPrePopulateMuscles().map { muscleEntities -> muscleEntities.toDomain() })
        }
    }

    override fun getMuscle(tag: String): Flow<Muscle> {
        return musclesDao.getMuscle(tag).map { it.toDomain() }
    }

    override suspend fun insertMuscle(muscles: List<Muscle>) =
        musclesDao.insertMuscles(muscles.map { it.toEntity() })

}