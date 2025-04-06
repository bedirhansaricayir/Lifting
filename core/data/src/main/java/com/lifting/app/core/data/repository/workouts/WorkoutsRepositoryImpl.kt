package com.lifting.app.core.data.repository.workouts

import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.WorkoutsDao
import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.toDomain
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutWithExtraInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

class WorkoutsRepositoryImpl @Inject constructor(
    private val workoutsDao: WorkoutsDao
) : WorkoutsRepository {
    override fun getWorkout(workoutId: String): Flow<Workout> =
        workoutsDao.getWorkout(workoutId).filterNotNull().map { it.toDomain() }

    override fun getWorkoutsWithExtraInfo(
        dateStart: LocalDate?,
        dateEnd: LocalDate?
    ): Flow<List<WorkoutWithExtraInfo>> {
        return if (dateStart != null && dateEnd != null) {
            workoutsDao.getWorkoutsWithExtraInfo(
                dateStart = dateStart.toEpochMillis(),
                dateEnd.toEpochMillis()
            ).map { it.toDomain() }
        } else {
            workoutsDao.getWorkoutsWithExtraInfo().map { it.toDomain() }
        }
    }

    override suspend fun updateWorkout(workout: Workout) =
        workoutsDao.updateWorkout(workout.toEntity().copy(updatedAt = LocalDateTime.now()))

    override suspend fun addExerciseToWorkout(workoutId: String, exerciseId: String) =
        workoutsDao.insertExerciseWorkoutJunction(
            ExerciseWorkoutJunction(
                id = generateUUID,
                workoutId = workoutId,
                exerciseId = exerciseId
            )
        )

    override suspend fun addEmptySetToExercise(
        setNumber: Int,
        exerciseWorkoutJunc: ExerciseWorkoutJunc
    ): ExerciseLogEntry {
        val logId = generateUUID
        workoutsDao.insertExerciseLog(
            ExerciseLogEntity(
                id = logId,
                workoutId = exerciseWorkoutJunc.workoutId,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        )

        val entry = ExerciseLogEntryEntity(
            entryId = generateUUID,
            logId = logId,
            junctionId = exerciseWorkoutJunc.id,
            setNumber = setNumber,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        workoutsDao.insertExerciseLogEntry(entry)

        return entry.toDomain()
    }

    override suspend fun updateExerciseLogEntry(entry: ExerciseLogEntry) =
        workoutsDao.updateExerciseLogEntry(entry.toEntity().copy(updatedAt = LocalDateTime.now()))

    override suspend fun reorderEntriesGroupByDelete(
        entriesGroup: ArrayList<ExerciseLogEntry>,
        entryToDelete: ExerciseLogEntry
    ) = workoutsDao.reorderEntriesGroupByDelete(
        entriesGroup.map { it.toEntity() } as ArrayList<ExerciseLogEntryEntity>,
        entryToDelete.toEntity()
    )

    override suspend fun deleteExerciseFromWorkout(logEntriesWithExercise: LogEntriesWithExercise) {
        with(logEntriesWithExercise.toEntity()) {
            workoutsDao.deleteExerciseWorkoutJunction(junction)
            workoutsDao.deleteExerciseLogEntries(logEntries.map { it.entryId })
            workoutsDao.deleteExerciseLogs(logEntries.map { it.logId!! })
        }
    }

    override suspend fun addExerciseSetGroupNote(exerciseSetGroupNote: ExerciseSetGroupNote) =
        workoutsDao.insertExerciseSetGroupNote(exerciseSetGroupNote.toEntity())

    override suspend fun deleteExerciseSetGroupNote(exerciseSetGroupNoteId: String) =
        workoutsDao.deleteExerciseSetGroupNote(exerciseSetGroupNoteId)

    override suspend fun updateExerciseSetGroupNote(exerciseSetGroupNote: ExerciseSetGroupNote) =
        workoutsDao.updateExerciseSetGroupNote(exerciseSetGroupNote.toEntity())

    override suspend fun updateWarmUpSets(
        junction: LogEntriesWithExercise,
        sets: List<ExerciseLogEntry>
    ) = workoutsDao.updateWarmUpSets(junction.toEntity(), sets.map { it.toEntity() })

    override fun getLogEntriesWithExercise(workoutId: String): Flow<List<LogEntriesWithExercise>> =
        workoutsDao.getLogEntriesWithExerciseJunction(workoutId).map { it.toDomain() }

    override fun getLogEntriesWithExtraInfo(workoutId: String): Flow<List<LogEntriesWithExtraInfo>> =
        workoutsDao.getLogEntriesWithExtraInfo(workoutId).map { it.toDomain() }

    override fun getWorkoutsCount(): Flow<List<CountWithDate>> =
        workoutsDao.getWorkoutsCount().map { it.toDomain() }

    override fun getWorkoutsCountOnDateRange(dateStart: LocalDate, dateEnd: LocalDate) =
        workoutsDao.getWorkoutsCountOnDateRange(dateStart.toEpochMillis(), dateEnd.toEpochMillis())

}