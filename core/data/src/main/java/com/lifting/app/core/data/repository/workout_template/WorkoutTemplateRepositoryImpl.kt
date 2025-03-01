package com.lifting.app.core.data.repository.workout_template

import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.WorkoutTemplateDao
import com.lifting.app.core.database.dao.WorkoutsDao
import com.lifting.app.core.database.model.TemplateWithWorkoutResource
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.model.WorkoutTemplateEntity
import com.lifting.app.core.database.model.toDomain
import com.lifting.app.core.model.TemplateWithWorkout
import com.lifting.app.core.model.WorkoutTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 07.02.2025
 */

class WorkoutTemplateRepositoryImpl @Inject constructor(
    private val workoutTemplateDao: WorkoutTemplateDao,
    private val workoutsDao: WorkoutsDao,
): WorkoutTemplateRepository {
    override fun getTemplate(templateId: String): Flow<WorkoutTemplate> =
        workoutTemplateDao.getTemplate(templateId = templateId).map { it.toDomain() }

    override fun getTemplates(): Flow<List<TemplateWithWorkout>> =
        workoutTemplateDao.getTemplatesWithWorkouts().map { it.map(TemplateWithWorkoutResource::toDomain) }

    override suspend fun createTemplate(): String {
        val workoutId = insertWorkout()
        val templateId = UUID.randomUUID().toString()
        val templateEntity = WorkoutTemplateEntity(
            id = templateId,
            workoutId = workoutId,
            isHidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
        workoutTemplateDao.insertTemplate(templateEntity)
        return workoutId
    }

    private suspend fun insertWorkout(): String {
        val workoutId = UUID.randomUUID().toString()
        val workoutEntity = WorkoutEntity(
            id = workoutId,
            isHidden = true,
            inProgress = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
        workoutsDao.insertWorkout(workoutEntity)
        return workoutId
    }

    override suspend fun updateTemplate(workoutTemplate: WorkoutTemplate) =
        workoutTemplateDao.updateTemplate(workoutTemplate.toEntity())

    override suspend fun deleteTemplate(templateId: String) =
        workoutTemplateDao.deleteTemplate(templateId)

}