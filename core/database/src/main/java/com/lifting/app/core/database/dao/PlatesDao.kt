package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifting.app.core.database.model.PlateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 28.05.2025
 */

@Dao
interface PlatesDao {

    @Query("SELECT * FROM plates WHERE id = :id")
    fun getPlate(id: String): Flow<PlateEntity>

    @Query("SELECT * FROM plates ORDER BY weight")
    fun getPlates(): Flow<List<PlateEntity>>

    @Query("SELECT * FROM plates WHERE for_weight_unit = :forWeightUnit AND is_active = 1")
    fun getActivePlates(forWeightUnit: String): Flow<List<PlateEntity>>

    @Query("UPDATE plates SET is_active = :isActive WHERE id = :plateId")
    suspend fun updateIsActive(plateId: String, isActive: Boolean)

    @Insert
    suspend fun insertPlates(plates: List<PlateEntity>)

    @Query("DELETE FROM plates WHERE id = :plateId")
    suspend fun deletePlate(plateId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlate(plate: PlateEntity)
}