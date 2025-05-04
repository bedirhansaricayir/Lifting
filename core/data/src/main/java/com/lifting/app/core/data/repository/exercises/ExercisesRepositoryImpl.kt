package com.lifting.app.core.data.repository.exercises

import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseWithInfoResource
import com.lifting.app.core.database.model.ExerciseWithMuscleResource
import com.lifting.app.core.database.model.LogEntriesWithWorkoutResource
import com.lifting.app.core.database.model.toDomain
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.ExerciseWithMuscle
import com.lifting.app.core.model.LogEntriesWithWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

internal class ExercisesRepositoryImpl @Inject constructor(
    private val exercisesDao: ExercisesDao
): ExercisesRepository {
    override fun getExercise(exerciseId: String): Flow<Exercise> = exercisesDao.getExercise(exerciseId).map { it.toDomain() }

    override fun getAllExercises(): Flow<List<Exercise>> = exercisesDao.getAllExercises().map { it.map(ExerciseEntity::toDomain)}

    override fun getAllExerciseWithInfo(searchQuery: String?): Flow<List<ExerciseWithInfo>> = exercisesDao.getAllExercisesWithInfo(searchQuery).map { it.map(ExerciseWithInfoResource::toDomain) }

    override fun getAllExercisesWithMuscle(): Flow<List<ExerciseWithMuscle>> = exercisesDao.getAllExercisesWithMuscles().map { it.map(ExerciseWithMuscleResource::toDomain) }

    override fun getAllLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>> = exercisesDao.getAllLogEntries(exerciseId).map { it.map(LogEntriesWithWorkoutResource::toDomain) }

    override fun getVisibleLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>> = exercisesDao.getVisibleLogEntries(exerciseId).map { it.map(LogEntriesWithWorkoutResource::toDomain) }

    override fun getVisibleLogEntriesCount(exerciseId: String): Flow<Long> = exercisesDao.getVisibleLogEntriesCount(exerciseId)

    override suspend fun createExercise(
        name: String?,
        notes: String?,
        primaryMuscleTag: String?,
        secondaryMuscleTag: String?,
        category: ExerciseCategory?
    ) = exercisesDao.insertExercise(
        ExerciseEntity(
            exerciseId = generateUUID(),
            name = name,
            notes = notes,
            primaryMuscleTag = primaryMuscleTag,
            secondaryMuscleTag = secondaryMuscleTag,
            category = category,
        )
    )
}