package com.lifting.app.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.database.model.CountWithDateEntity
import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseSetGroupNoteEntity
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.LogEntriesWithExerciseResource
import com.lifting.app.core.database.model.LogEntriesWithExtraInfoJunction
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.model.WorkoutWithExtraInfoResource
import com.lifting.app.core.model.LogSetType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@Dao
interface WorkoutsDao {
    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    fun getWorkout(workoutId: String): Flow<WorkoutEntity>

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Update
    suspend fun updateWorkout(workoutEntity: WorkoutEntity)

    @Query("DELETE FROM workouts WHERE id = :workoutId")
    suspend fun deleteWorkoutById(workoutId: String)

    @Insert
    suspend fun insertExerciseWorkoutJunction(exerciseWorkoutJunction: ExerciseWorkoutJunction)

    @Insert
    suspend fun insertExerciseLog(log: ExerciseLogEntity)

    @Insert
    suspend fun insertExerciseLogEntry(logEntryEntity: ExerciseLogEntryEntity)

    @Update
    suspend fun updateExerciseLogEntry(logEntryEntity: ExerciseLogEntryEntity)

    @Transaction
    suspend fun reorderEntriesGroupByDelete(
        entriesGroup: ArrayList<ExerciseLogEntryEntity>,
        entryToDelete: ExerciseLogEntryEntity
    ) {
        deleteExerciseLogEntryById(entryToDelete.entryId)

        entriesGroup.remove(entriesGroup.find { it.entryId == entryToDelete.entryId })

        for (groupEntry in entriesGroup) {
            val index = entriesGroup.indexOf(groupEntry)
            updateExerciseLogEntry(
                groupEntry.copy(
                    setNumber = index + 1,
                    updatedAt = LocalDateTime.now()
                )
            )
        }
    }

    @Query("DELETE FROM exercise_log_entries WHERE entry_id = :entryId")
    suspend fun deleteExerciseLogEntryById(entryId: String)

    @Delete
    suspend fun deleteExerciseWorkoutJunction(exerciseWorkoutJunction: ExerciseWorkoutJunction)

    @Query("DELETE FROM exercise_log_entries WHERE entry_id IN (:ids)")
    suspend fun deleteExerciseLogEntries(ids: List<String>)

    @Query("DELETE FROM exercise_logs WHERE id IN (:ids)")
    suspend fun deleteExerciseLogs(ids: List<String>)

    @Insert
    suspend fun insertExerciseSetGroupNote(exerciseSetGroupNoteEntity: ExerciseSetGroupNoteEntity)

    @Query("DELETE FROM exercise_set_group_notes WHERE id = :exerciseSetGroupNoteId")
    suspend fun deleteExerciseSetGroupNote(exerciseSetGroupNoteId: String)

    @Update
    suspend fun updateExerciseSetGroupNote(exerciseSetGroupNoteEntity: ExerciseSetGroupNoteEntity)

    @Transaction
    @Query("SELECT * FROM exercise_workout_junctions WHERE workout_id = :workoutId")
    fun getLogEntriesWithExerciseJunction(workoutId: String): Flow<List<LogEntriesWithExerciseResource>>

    @Transaction
    suspend fun updateWarmUpSets(
        junction: LogEntriesWithExerciseResource,
        warmUpSets: List<ExerciseLogEntryEntity>
    ) {
        val time = LocalDateTime.now()

        val junctionId = junction.junction.id

        val sortedEntries = junction.logEntries.sortedWith { left, right ->
            left.setNumber?.compareTo(right.setNumber ?: 0) ?: 0
        }
//        val newEntries = arrayListOf<ExerciseLogEntry>()
        val logsToAdd = arrayListOf<ExerciseLogEntity>()
        val entriesToAdd = arrayListOf<ExerciseLogEntryEntity>()
        val entriesToDelete = arrayListOf<ExerciseLogEntryEntity>()
        val entriesToUpdate = arrayListOf<ExerciseLogEntryEntity>()

        var mSetNumber = 0

        for (set in warmUpSets) {
            val entryId = generateUUID
            val logId = generateUUID

            val newEntry =
                set.copy(
                    entryId = entryId,
                    logId = logId,
                    junctionId = junctionId,
                    setNumber = mSetNumber + 1,
                    createdAt = time,
                    updatedAt = time,
                )

//            newEntries.add(
//                newEntry
//            )
            entriesToAdd.add(newEntry)
            logsToAdd.add(
                ExerciseLogEntity(
                    id = logId,
                    workoutId = junction.junction.workoutId,
                    createdAt = time,
                    updatedAt = time,
                )
            )
            mSetNumber++
        }

        for (entry in sortedEntries) {
            if (entry.setType == LogSetType.WARM_UP) {
                entriesToDelete.add(
                    entry
                )
            } else {
                val updatedEntry = entry.copy(
                    setNumber = mSetNumber + 1,
                    updatedAt = time
                )

//                newEntries.add(updatedEntry)

                entriesToUpdate.add(
                    updatedEntry
                )
                mSetNumber++
            }
        }



        deleteExerciseLogEntries(entriesToDelete.map { it.entryId })
        deleteExerciseLogs(entriesToDelete.filter { it.logId != null }.map { it.logId!! })

        for (logToAdd in logsToAdd) {
            insertExerciseLog(logToAdd)
        }

        for (entryToAdd in entriesToAdd) {
            insertExerciseLogEntry(entryToAdd)
        }

        for (entryToUpdate in entriesToUpdate) {
            updateExerciseLogEntry(entryToUpdate)
        }
    }

    @Transaction
    @Query("SELECT * FROM exercise_workout_junctions j JOIN exercises e ON e.exercise_id = j.exercise_id LEFT JOIN muscles m ON m.tag = e.primary_muscle_tag WHERE workout_id = :workoutId")
    fun getLogEntriesWithExtraInfo(workoutId: String): Flow<List<LogEntriesWithExtraInfoJunction>>

    @Query(
        """
        SELECT * FROM workouts w
        WHERE w.is_hidden = 0 AND w.in_progress = 0 
        ORDER BY w.completed_at DESC
        """
    )
    fun getWorkoutsWithExtraInfo(): Flow<List<WorkoutWithExtraInfoResource>>

    @Query(
        """
        SELECT * FROM workouts w
        WHERE
        date(start_at / 1000,'unixepoch') >= date(:dateStart / 1000,'unixepoch') AND date(start_at / 1000,'unixepoch') <= date(:dateEnd / 1000,'unixepoch') 
        AND w.is_hidden = 0 AND w.in_progress = 0 
        ORDER BY w.completed_at DESC
        """
    )
    fun getWorkoutsWithExtraInfo(dateStart: Long, dateEnd: Long): Flow<List<WorkoutWithExtraInfoResource>>

    @Query("SELECT COUNT(*) as count, start_at as date FROM workouts WHERE is_hidden = 0 AND in_progress = 0 GROUP BY start_at")
    fun getWorkoutsCount(): Flow<List<CountWithDateEntity>>

    @Query(
        """
            SELECT SUM(count) FROM (SELECT COUNT(*) as count FROM workouts WHERE 
    date(start_at / 1000,'unixepoch') >= date(:dateStart / 1000,'unixepoch') AND
     date(start_at / 1000,'unixepoch') <= date(:dateEnd / 1000,'unixepoch') 
    AND is_hidden = 0 AND in_progress = 0 GROUP BY start_at)
    """)
    fun getWorkoutsCountOnDateRange(dateStart: Long, dateEnd: Long): Flow<Long>

    @Transaction
    @Query("SELECT exercise_log_entries.* FROM exercise_log_entries JOIN exercise_workout_junctions j WHERE j.workout_id = :workoutId AND j.id = junction_id")
    fun getLogEntriesByWorkoutId(workoutId: String): Flow<List<ExerciseLogEntryEntity>>

    @Query("SELECT * FROM exercise_workout_junctions WHERE workout_id = :workoutId")
    suspend fun getExerciseWorkoutJunctionsNonFlow(workoutId: String): List<ExerciseWorkoutJunction>

    @Query("DELETE FROM exercise_log_entries WHERE junction_id IN (:junctionIds)")
    suspend fun deleteAllLogEntriesForJunctionIds(junctionIds: List<String>)

    @Query("DELETE FROM exercise_logs WHERE workout_id = :workoutId")
    suspend fun deleteAllLogsForWorkoutId(workoutId: String)

    @Query("DELETE FROM exercise_workout_junctions WHERE id IN (:ids)")
    suspend fun deleteExerciseWorkoutJunctions(ids: List<String>)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

}