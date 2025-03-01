package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifting.app.core.database.model.BarbellEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Dao
interface BarbellsDao {
    @Query("SELECT * FROM barbells WHERE id = :id")
    fun getBarbell(id: String): Flow<BarbellEntity>

    @Query("SELECT * FROM barbells ORDER BY name")
    fun getBarbells(): Flow<List<BarbellEntity>>

    @Query("SELECT * FROM barbells WHERE is_active = 1 ORDER BY name")
    fun getActiveBarbells(): Flow<List<BarbellEntity>>

    @Query("UPDATE barbells SET is_active = :isActive WHERE id = :barbellId")
    suspend fun updateIsActive(barbellId: String, isActive: Boolean)

    @Insert
    suspend fun insertBarbells(barbells: List<BarbellEntity>)

    @Query("DELETE FROM barbells WHERE id = :barbellId")
    suspend fun deleteBarbell(barbellId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarbell(barbell: BarbellEntity)
}