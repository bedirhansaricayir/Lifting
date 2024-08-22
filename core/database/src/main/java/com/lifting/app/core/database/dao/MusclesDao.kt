package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifting.app.core.database.model.MuscleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 21.08.2024
 */

@Dao
interface MusclesDao {

    @Query("SELECT * FROM muscles WHERE tag = :tag")
    fun getMuscle(tag: String): Flow<MuscleEntity>

    @Query("SELECT * FROM muscles")
    fun getMuscles(): Flow<List<MuscleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscles(muscles: List<MuscleEntity>)
}