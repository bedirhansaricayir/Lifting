package com.lifting.app.core.data.repository.workouts

import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.common.utils.Constants.NONE_WORKOUT_ID
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.data.mapper.Mapper.toDomain
import com.lifting.app.core.data.mapper.Mapper.toEntity
import com.lifting.app.core.database.dao.WorkoutTemplateDao
import com.lifting.app.core.database.dao.WorkoutsDao
import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity.Companion.calculateTotalVolume
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.datastore.PreferencesStorage
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.MaxDurationPR
import com.lifting.app.core.model.MaxWeightPR
import com.lifting.app.core.model.PersonalRecord
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.core.model.addIfNot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

class WorkoutsRepositoryImpl @Inject constructor(
    private val workoutsDao: WorkoutsDao,
    private val templateDao: WorkoutTemplateDao,
    private val preferencesStorage: PreferencesStorage,
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

    override fun getWorkoutsWithExtraInfoByDate(date: LocalDate): Flow<List<WorkoutWithExtraInfo>> =
        workoutsDao.getWorkoutsWithExtraInfoByDate(date.toEpochMillis()).map { it.toDomain() }

    override fun getActiveWorkoutId(): Flow<String> =
        preferencesStorage.activeWorkoutId

    override suspend fun setActiveWorkoutId(workoutId: String) {
        preferencesStorage.setActiveWorkoutId(workoutId)
    }

    override suspend fun updateWorkout(workout: Workout) =
        workoutsDao.updateWorkout(workout.toEntity().copy(updatedAt = LocalDateTime.now()))

    override suspend fun addExerciseToWorkout(workoutId: String, exerciseId: String) =
        workoutsDao.insertExerciseWorkoutJunction(
            ExerciseWorkoutJunction(
                id = generateUUID(),
                workoutId = workoutId,
                exerciseId = exerciseId
            )
        )

    override suspend fun addEmptySetToExercise(
        setNumber: Int,
        exerciseWorkoutJunc: ExerciseWorkoutJunc
    ): ExerciseLogEntry {
        val logId = generateUUID()
        val now = LocalDateTime.now()
        workoutsDao.insertExerciseLog(
            ExerciseLogEntity(
                id = logId,
                workoutId = exerciseWorkoutJunc.workoutId,
                createdAt = now,
                updatedAt = now,
            )
        )

        val entry = ExerciseLogEntryEntity(
            entryId = generateUUID(),
            logId = logId,
            junctionId = exerciseWorkoutJunc.id,
            setNumber = setNumber,
            createdAt = now,
            updatedAt = now
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

    override fun getWorkouts(): Flow<List<Workout>> =
        workoutsDao.getWorkouts().map { it.toDomain() }

    override fun getWorkoutsCountOnDateRange(dateStart: LocalDate, dateEnd: LocalDate) =
        workoutsDao.getWorkoutsCountOnDateRange(dateStart.toEpochMillis(), dateEnd.toEpochMillis())

    override fun getTotalVolumeLiftedByWorkoutId(workoutId: String): Flow<Double> =
        workoutsDao.getLogEntriesByWorkoutId(workoutId).map { it.calculateTotalVolume() }

    override suspend fun deleteWorkoutWithAllDependencies(workoutId: String) {
        val workout = getWorkout(workoutId).firstOrNull()
        workout?.let { workout ->
            // Get all ExerciseWorkoutJunctions related to workout
            val junctions = workoutsDao.getExerciseWorkoutJunctionsNonFlow(workout.id)
            // Delete all ExerciseLogEntries for workout using junction ids
            workoutsDao.deleteAllLogEntriesForJunctionIds(junctionIds = junctions.map { it.id })
            // Delete all ExerciseLogs for workout
            workoutsDao.deleteAllLogsForWorkoutId(workoutId = workout.id)
            // Delete all junctions related to workout
            workoutsDao.deleteExerciseWorkoutJunctions(junctions.map { it.id })
            // Delete workout
            workoutsDao.deleteWorkout(workout.toEntity())
        }
    }

    override fun getExerciseLogByLogId(logId: String): Flow<ExerciseLogEntity> =
        workoutsDao.getExerciseLogByLogId(logId)

    private suspend fun checkIfWorkoutIsActive(discardActive: Boolean): Boolean {
        val activeWorkoutId = getActiveWorkoutId().firstOrNull()
        return when {
            activeWorkoutId.isNullOrBlank() || activeWorkoutId == NONE_WORKOUT_ID -> false
            discardActive -> {
                deleteWorkoutWithAllDependencies(activeWorkoutId)
                setActiveWorkoutId(NONE_WORKOUT_ID)
                false
            }

            else -> true
        }
    }

    override suspend fun startWorkoutFromTemplate(
        templateId: String,
        discardActive: Boolean,
        onWorkoutAlreadyActive: () -> Unit
    ) {
        val isActive = checkIfWorkoutIsActive(discardActive)

        if (isActive) {
            onWorkoutAlreadyActive()
            return
        }
        val template = templateDao.getTemplate(templateId).firstOrNull() ?: return

        if (template.workoutId == null) return

        templateDao.updateTemplate(
            template.copy(
                lastPerformedAt = LocalDateTime.now()
            )
        )

        startWorkout(template.workoutId!!)
    }

    override suspend fun startWorkoutFromWorkout(
        workoutId: String,
        discardActive: Boolean,
        onWorkoutAlreadyActive: () -> Unit
    ) {
        val isActive = checkIfWorkoutIsActive(discardActive)

        if (isActive) {
            onWorkoutAlreadyActive()
            return
        }

        startWorkout(workoutId)
    }

    private suspend fun startWorkout(workoutId: String) {
        val actualWorkout = workoutsDao.getWorkout(workoutId).filterNotNull().first()

        val exerciseWorkoutJunctionsToAdd = arrayListOf<ExerciseWorkoutJunction>()
        val exerciseLogsToAdd = arrayListOf<ExerciseLogEntity>()
        val exerciseLogEntriesToAdd = arrayListOf<ExerciseLogEntryEntity>()

        val newWorkoutId = generateUUID()
        val now = LocalDateTime.now()
        val newWorkout = actualWorkout.copy(
            id = newWorkoutId,
            isHidden = false,
            inProgress = true,
            startAt = now,
            createdAt = now,
            updatedAt = now
        )

        val fromWithJunctions = workoutsDao.getLogEntriesWithExerciseJunction(workoutId).first()

        for (withJunction in fromWithJunctions) {
            val newJunctionId = generateUUID()
            val newJunction = withJunction.junction.copy(
                id = newJunctionId,
                workoutId = newWorkoutId
            )

            exerciseWorkoutJunctionsToAdd.add(newJunction)

            for (entry in withJunction.logEntries) {
                val newEntryId = generateUUID()

                val newLogId = if (entry.logId != null) {
                    val newExerciseLogId = generateUUID()
                    val oldExerciseLog = getExerciseLogByLogId(entry.logId!!).first()
                    val newExerciseLog = oldExerciseLog.copy(
                        id = newExerciseLogId,
                        workoutId = newWorkoutId,
                        createdAt = now,
                        updatedAt = now
                    )
                    exerciseLogsToAdd.add(newExerciseLog)
                    newExerciseLogId
                } else {
                    null
                }

                val newEntry = entry.copy(
                    entryId = newEntryId,
                    personalRecords = null,
                    completed = false,
                    logId = newLogId,
                    junctionId = newJunctionId,
                    createdAt = now,
                    updatedAt = now
                )

                exerciseLogEntriesToAdd.add(newEntry)
            }

        }

        workoutsDao.insertWorkout(newWorkout)

        for (junction in exerciseWorkoutJunctionsToAdd) {
            workoutsDao.insertExerciseWorkoutJunction(junction)
        }

        for (exerciseLog in exerciseLogsToAdd) {
            workoutsDao.insertExerciseLog(exerciseLog)
        }

        for (entry in exerciseLogEntriesToAdd) {
            workoutsDao.insertExerciseLogEntry(entry)
        }

        setActiveWorkoutId(newWorkoutId)

    }

    override suspend fun finishWorkout(workoutId: String) {
        val workout = workoutsDao.getWorkout(workoutId).first()
        val now = LocalDateTime.now()
        val updatedWorkout = workout.copy(
            inProgress = false,
            isHidden = false,
            personalRecords = null,
            completedAt = now,
            updatedAt = now
        )

        val prs = buildList<PersonalRecord> {
            val lastMaxDuration = workoutsDao.getLongestWorkoutDuration().firstOrNull()

            if (lastMaxDuration == null || (updatedWorkout.getDuration() ?: 0) > lastMaxDuration) {
                addIfNot(MaxDurationPR())
            }
        }

        updatedWorkout.personalRecords = prs

        val junctions = workoutsDao.getLogEntriesWithExerciseJunction(workoutId).first()

        junctions.forEach { junction ->
            val lastMaxWeightInExercise =
                workoutsDao.getMaxWeightLiftedInExercise(junction.exercise.exerciseId).firstOrNull()
                    ?: 0.0

            junction.logEntries.sortedByDescending { it.weight }.getOrNull(0)
                ?.let { maxWeightEntry ->
                    if ((maxWeightEntry.weight ?: 0.0) > lastMaxWeightInExercise) {
                        val entryPrs = buildList {
                            maxWeightEntry.personalRecords?.let { addAll(it) }
                            add(MaxWeightPR())

                        }
                        workoutsDao.updateExerciseLogEntry(
                            maxWeightEntry.copy(
                                personalRecords = entryPrs
                            )
                        )
                    }
                }
        }

        workoutsDao.updateWorkout(updatedWorkout)
    }

    override suspend fun createWorkout(
        workoutId: String,
        workoutName: String,
        discardActive: Boolean,
        onWorkoutAlreadyActive: () -> Unit
    ): Boolean {
        val isActive = checkIfWorkoutIsActive(discardActive)

        if (isActive) {
            onWorkoutAlreadyActive()
            return false
        }
        val now = LocalDateTime.now()
        val workout = Workout(
            id = workoutId,
            name = workoutName,
            inProgress = true,
            isHidden = true,
            startAt = now,
            createdAt = now,
            updatedAt = now,
            completedAt = null,
            personalRecords = null,
            duration = null,
            note = null
        )
        workoutsDao.insertWorkout(workout.toEntity())
        return true
    }

    override suspend fun updateExerciseWorkoutJunctionBarbellId(
        junctionId: String,
        barbellId: String
    ) = workoutsDao.updateExerciseWorkoutJunctionBarbellId(junctionId, barbellId)

    override suspend fun updateExerciseWorkoutJunctionSupersetId(
        junctionId: String,
        supersetId: Int?
    ) = workoutsDao.updateExerciseWorkoutJunctionSupersetId(junctionId, supersetId)


}