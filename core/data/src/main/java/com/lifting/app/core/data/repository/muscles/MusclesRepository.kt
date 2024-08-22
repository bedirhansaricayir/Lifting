package com.lifting.app.core.data.repository.muscles

import com.lifting.app.core.model.Muscle
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
interface MusclesRepository {
    fun getMuscles(): Flow<List<Muscle>>
    fun getMuscle(tag: String): Flow<Muscle>
    suspend fun insertMuscle(muscles: List<Muscle>)
}