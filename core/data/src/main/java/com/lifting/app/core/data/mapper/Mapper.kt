package com.lifting.app.core.data.mapper

import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseSetGroupNoteEntity
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.LogEntriesWithExerciseResource
import com.lifting.app.core.database.model.LogEntriesWithExtraInfoJunction
import com.lifting.app.core.database.model.MuscleEntity
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.model.WorkoutTemplateEntity
import com.lifting.app.core.database.model.WorkoutWithExtraInfoResource
import com.lifting.app.core.database.model.calculateTotalVolume
import com.lifting.app.core.database.model.getTotalPRs
import com.lifting.app.core.database.model.toDomain
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.Muscle
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutTemplate
import com.lifting.app.core.model.WorkoutWithExtraInfo

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
object Mapper {
    fun MuscleEntity.toDomain(): Muscle = with(this) {
        Muscle(
            tag = tag,
            name = name,
            type = type,
            isDeletable = isDeletable,
            isHidden = isHidden
        )
    }

    fun Muscle.toEntity(): MuscleEntity = with(this) {
        MuscleEntity(
            tag = tag,
            name = name,
            type = type,
            isDeletable = isDeletable,
            isHidden = isHidden
        )
    }

    fun WorkoutTemplate.toEntity(): WorkoutTemplateEntity = with(this) {
        WorkoutTemplateEntity(
            id = id,
            isHidden = isHidden,
            workoutId = workoutId,
            lastPerformedAt = lastPerformedAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun Workout.toEntity(): WorkoutEntity = with(this) {
        WorkoutEntity(
            id = id,
            name = name,
            note = note,
            inProgress = inProgress,
            isHidden = isHidden,
            startAt = startAt,
            completedAt = completedAt,
            personalRecords = personalRecords,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun ExerciseWorkoutJunc.toEntity(): ExerciseWorkoutJunction = with(this) {
        ExerciseWorkoutJunction(
            id  = id,
            supersetId = supersetId,
            barbellId = barbellId,
            exerciseId = exerciseId,
            workoutId = workoutId,
        )
    }

    fun ExerciseLogEntry.toEntity(): ExerciseLogEntryEntity = with(this) {
        ExerciseLogEntryEntity(
            entryId = entryId,
            logId = logId,
            junctionId = junctionId,
            setNumber = setNumber,
            setType = setType,
            weight = weight,
            reps = reps,
            rpe = rpe,
            completed = completed,
            timeRecorded = timeRecorded,
            distance = distance,
            weight_unit = weightUnit,
            distance_unit  = distanceUnit,
            personalRecords = personalRecords,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun ExerciseSetGroupNote.toEntity(): ExerciseSetGroupNoteEntity = with(this) {
        ExerciseSetGroupNoteEntity(
            id = id,
            note = note,
            exerciseWorkoutJunctionId = exerciseWorkoutJunctionId,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    fun Exercise.toEntity(): ExerciseEntity = with(this) {
        ExerciseEntity(
            exerciseId = exerciseId,
            name = name,
            notes = notes,
            primaryMuscleTag = primaryMuscleTag,
            secondaryMuscleTag = secondaryMuscleTag,
            category = category,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }


    fun LogEntriesWithExercise.toEntity(): LogEntriesWithExerciseResource = with(this) {
        LogEntriesWithExerciseResource(
            junction = junction.toEntity(),
            exercise = exercise.toEntity(),
            logEntries = logEntries.map { it.toEntity() },
            notes = notes?.map { it.toEntity() }
        )
    }

    fun List<LogEntriesWithExerciseResource>.toDomain(): List<LogEntriesWithExercise> = with(this) {
        this.map { it.toDomain() }
    }
    fun LogEntriesWithExerciseResource.toDomain(): LogEntriesWithExercise = with(this) {
        LogEntriesWithExercise(
            junction = junction.toDomain(),
            exercise = exercise.toDomain(),
            logEntries = logEntries.map { it.toDomain() },
            notes = notes?.map { it.toDomain() }
        )
    }

    @JvmName("toDomainLogEntriesWithExtraInfoJunction")
    fun List<LogEntriesWithExtraInfoJunction>.toDomain(): List<LogEntriesWithExtraInfo> = with(this) {
        this.map { it.toDomain() }
    }

    fun LogEntriesWithExtraInfoJunction.toDomain(): LogEntriesWithExtraInfo = with(this) {
        LogEntriesWithExtraInfo(
            junction = junction.toDomain(),
            exercise = exercise.toDomain(),
            primaryMuscle = primaryMuscle.toDomain(),
            logEntries = logEntries.map { it.toDomain() }
        )
    }

    @JvmName("toDomainWorkoutWithExtraInfoResource")
    fun List<WorkoutWithExtraInfoResource>.toDomain(): List<WorkoutWithExtraInfo> = this.map { it.toDomain() }

    fun WorkoutWithExtraInfoResource.toDomain(): WorkoutWithExtraInfo {
        val logEntries = junctions.flatMap { j -> j.logEntries }
        return WorkoutWithExtraInfo(
            workout = workout.toDomain(),
            totalVolume = logEntries.calculateTotalVolume(),
            totalExercises = junctions.size,
            totalPRs = logEntries.getTotalPRs(workout.personalRecords?.size)
        )
    }
}