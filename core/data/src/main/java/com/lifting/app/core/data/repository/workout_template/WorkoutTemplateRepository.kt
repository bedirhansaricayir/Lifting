package com.lifting.app.core.data.repository.workout_template

import com.lifting.app.core.model.TemplateWithWorkout
import com.lifting.app.core.model.WorkoutTemplate
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 07.02.2025
 */
interface WorkoutTemplateRepository {
    fun getTemplate(templateId: String): Flow<WorkoutTemplate>

    fun getTemplates(): Flow<List<TemplateWithWorkout>>

    suspend fun createTemplate(): String

    suspend fun updateTemplate(workoutTemplate: WorkoutTemplate)

    suspend fun deleteTemplate(templateId: String)
}