package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseWithInfoResource
import com.lifting.app.core.database.model.ExerciseWithMuscleResource
import com.lifting.app.core.database.model.LogEntriesWithWorkoutResource
import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

@Dao
interface ExercisesDao {

    @Query("SELECT * FROM exercises WHERE exercise_id = :exerciseId")
    fun getExercise(exerciseId: String): Flow<ExerciseEntity>

    @Query("SELECT * FROM exercises ORDER BY name")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Query(
        """
            SELECT 
            e.*,
            (SELECT 
                COUNT(ewj.id)
            FROM exercise_workout_junctions ewj 
            JOIN workouts w ON ewj.workout_id = w.id AND e.exercise_id = ewj.exercise_id 
            WHERE w.id = ewj.workout_id AND w.is_hidden = 0 AND w.in_progress = 0) as logs_count 
        FROM exercises e WHERE CASE WHEN :searchQuery IS NOT NULL
         THEN e.name LIKE '%' || :searchQuery || '%'
         ELSE 1
    END ORDER BY e.name COLLATE NOCASE ASC
    """
    )
    fun getAllExercisesWithInfo(searchQuery: String?): Flow<List<ExerciseWithInfoResource>>

    @Query("SELECT * FROM exercises ORDER BY name")
    fun getAllExercisesWithMuscles(): Flow<List<ExerciseWithMuscleResource>>

    @Transaction
    @Query("SELECT * FROM exercise_workout_junctions WHERE exercise_id = :exerciseId")
    fun getAllLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkoutResource>>

    @Transaction
    @Query("SELECT exercise_workout_junctions.* FROM exercise_workout_junctions JOIN workouts WHERE exercise_id = :exerciseId AND workouts.id = workout_id AND workouts.is_hidden = 0 AND workouts.in_progress = 0 ORDER BY start_at DESC")
    fun getVisibleLogEntries(exerciseId: String): Flow<List<LogEntriesWithWorkoutResource>>

    @Transaction
    @Query("SELECT COUNT(exercise_workout_junctions.id) FROM exercise_workout_junctions JOIN workouts WHERE exercise_id = :exerciseId AND workouts.id = workout_id AND workouts.is_hidden = 0 AND workouts.in_progress = 0 ORDER BY start_at")
    fun getVisibleLogEntriesCount(exerciseId: String): Flow<Long>

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)
}