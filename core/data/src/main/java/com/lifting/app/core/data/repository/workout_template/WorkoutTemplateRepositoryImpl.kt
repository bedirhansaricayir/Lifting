package com.lifting.app.core.data.repository.workout_template

import com.lifting.app.core.common.utils.generateUUID
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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 07.02.2025
 */

class WorkoutTemplateRepositoryImpl @Inject constructor(
    private val workoutTemplateDao: WorkoutTemplateDao,
    private val workoutsDao: WorkoutsDao,
): WorkoutTemplateRepository {
    override fun getTemplate(templateId: String): Flow<WorkoutTemplate> =
        workoutTemplateDao.getTemplate(templateId = templateId).filterNotNull().map { it.toDomain() }

    override fun getTemplates(): Flow<List<TemplateWithWorkout>> =
        workoutTemplateDao.getTemplatesWithWorkouts().map { it.map(TemplateWithWorkoutResource::toDomain) }

    override suspend fun createTemplate(): String {
        val workoutId = insertWorkout()
        val templateId = generateUUID()
        val now = LocalDateTime.now()
        val templateEntity = WorkoutTemplateEntity(
            id = templateId,
            workoutId = workoutId,
            isHidden = false,
            createdAt = now,
            updatedAt = now,
        )
        workoutTemplateDao.insertTemplate(templateEntity)
        return workoutId
    }

    private suspend fun insertWorkout(): String {
        val workoutId = generateUUID()
        val now = LocalDateTime.now()
        val workoutEntity = WorkoutEntity(
            id = workoutId,
            isHidden = false,
            inProgress = false,
            createdAt = now,
            updatedAt = now,
        )
        workoutsDao.insertWorkout(workoutEntity)
        return workoutId
    }

    override suspend fun updateTemplate(workoutTemplate: WorkoutTemplate) =
        workoutTemplateDao.updateTemplate(workoutTemplate.toEntity())

    override suspend fun deleteTemplate(templateId: String) {
        val template = workoutTemplateDao.getTemplate(templateId).firstOrNull()
        template?.let {
            workoutTemplateDao.deleteTemplate(it.id)
            if (it.workoutId != null) {
                workoutsDao.deleteWorkoutById(it.workoutId!!)
            }
        }
    }

}