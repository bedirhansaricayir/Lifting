package com.lifting.app.core.data.repository.workouts

import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutWithExtraInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.ArrayList

/**
 * Created by bedirhansaricayir on 08.02.2025
 */
interface WorkoutsRepository {
    fun getWorkout(workoutId: String): Flow<Workout>
    fun getWorkoutsWithExtraInfo(dateStart: LocalDate? = null, dateEnd: LocalDate? = null): Flow<List<WorkoutWithExtraInfo>>
    fun getActiveWorkoutId(): Flow<String>
    suspend fun setActiveWorkoutId(workoutId: String)
    suspend fun updateWorkout(workout: Workout)
    suspend fun addExerciseToWorkout(workoutId: String, exerciseId: String)
    suspend fun addEmptySetToExercise(setNumber: Int, exerciseWorkoutJunc: ExerciseWorkoutJunc): ExerciseLogEntry
    suspend fun updateExerciseLogEntry(entry: ExerciseLogEntry)
    suspend fun reorderEntriesGroupByDelete(
        entriesGroup: ArrayList<ExerciseLogEntry>,
        entryToDelete: ExerciseLogEntry,
    )
    suspend fun deleteExerciseFromWorkout(logEntriesWithExercise: LogEntriesWithExercise)


    suspend fun addExerciseSetGroupNote(exerciseSetGroupNote: ExerciseSetGroupNote)
    suspend fun deleteExerciseSetGroupNote(exerciseSetGroupNoteId: String)
    suspend fun updateExerciseSetGroupNote(exerciseSetGroupNote: ExerciseSetGroupNote)

    suspend fun updateWarmUpSets(
        junction: LogEntriesWithExercise,
        sets: List<ExerciseLogEntry>
    )

    fun getLogEntriesWithExercise(workoutId: String): Flow<List<LogEntriesWithExercise>>

    fun getLogEntriesWithExtraInfo(workoutId: String): Flow<List<LogEntriesWithExtraInfo>>

    fun getWorkoutsCount(): Flow<List<CountWithDate>>
    fun getWorkoutsCountOnDateRange(dateStart: LocalDate, dateEnd: LocalDate): Flow<Long>
    fun getTotalVolumeLiftedByWorkoutId(workoutId: String): Flow<Double>

    suspend fun deleteWorkoutWithAllDependencies(workoutId: String)

    fun getExerciseLogByLogId(logId: String): Flow<ExerciseLogEntity>
    suspend fun startWorkoutFromTemplate(
        templateId: String,
        discardActive: Boolean,
        onWorkoutAlreadyActive: () -> Unit
    )

}