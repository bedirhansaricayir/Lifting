package com.lifting.app.core.data.repository.exercises

import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.ExerciseWithMuscle
import com.lifting.app.core.model.LogEntriesWithWorkout
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

interface ExercisesRepository {

    fun getExercise(exerciseId: String): Flow<Exercise>

    fun getAllExercises(): Flow<List<Exercise>>

    fun getAllExerciseWithInfo(searchQuery: String?): Flow<List<ExerciseWithInfo>>

    fun getAllExercisesWithMuscle(): Flow<List<ExerciseWithMuscle>>

    fun getAllLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>>

    fun getVisibleLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkout>>

    fun getVisibleLogEntriesCount(exerciseId: String): Flow<Long>

    suspend fun createExercise(
        name: String? = null,
        notes: String? = null,
        primaryMuscleTag: String? = null,
        secondaryMuscleTag: String? = null,
        category: ExerciseCategory? = null,
    )
}