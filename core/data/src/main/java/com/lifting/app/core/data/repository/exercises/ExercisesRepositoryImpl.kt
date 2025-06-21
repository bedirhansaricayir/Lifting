package com.lifting.app.core.data.repository.exercises

import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.LogEntriesWithWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

internal class ExercisesRepositoryImpl @Inject constructor(
    private val exercisesDao: ExercisesDao,
) : ExercisesRepository {
    override fun getExercise(exerciseId: String): Flow<Exercise> =
        exercisesDao.getExercise(exerciseId).map { it.toDomain() }

    override fun getAllExercises(): Flow<List<Exercise>> =
        exercisesDao.getAllExercises().map { it.toDomain() }

    override fun getAllExerciseWithInfo(searchQuery: String?): Flow<List<ExerciseWithInfo>> =
        exercisesDao.getAllExercisesWithInfo(searchQuery).map { it.toDomain() }

    override fun getAllLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>> =
        exercisesDao.getAllLogEntries(exerciseId).map { it.toDomain()}

    override fun getVisibleLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>> =
        exercisesDao.getVisibleLogEntries(exerciseId).map { it.toDomain() }

    override fun getVisibleLogEntriesCount(exerciseId: String): Flow<Long> =
        exercisesDao.getVisibleLogEntriesCount(exerciseId)

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

    override suspend fun insertExercises(exercises: List<Exercise>) =
        exercisesDao.insertExercises(exercises.map { it.toEntity() })

}