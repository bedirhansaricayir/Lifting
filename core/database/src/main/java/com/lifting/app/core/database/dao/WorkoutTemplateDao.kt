package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lifting.app.core.database.model.TemplateWithWorkoutResource
import com.lifting.app.core.database.model.WorkoutTemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 07.02.2025
 */

@Dao
interface WorkoutTemplateDao {
    @Insert
    suspend fun insertTemplate(workoutTemplate: WorkoutTemplateEntity)

    @Update
    suspend fun updateTemplate(workoutTemplate: WorkoutTemplateEntity)

    @Query("DELETE FROM workout_templates WHERE id = :templateId")
    suspend fun deleteTemplate(templateId: String)

    @Query("SELECT * FROM workout_templates WHERE id = :templateId")
    fun getTemplate(templateId: String): Flow<WorkoutTemplateEntity>

    @Query("SELECT * FROM workout_templates WHERE is_hidden = 0")
    fun getTemplatesWithWorkouts(): Flow<List<TemplateWithWorkoutResource>>
}