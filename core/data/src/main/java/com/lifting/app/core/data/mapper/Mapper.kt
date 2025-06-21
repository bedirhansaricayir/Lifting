package com.lifting.app.core.data.mapper

import com.lifting.app.core.database.model.BarbellEntity
import com.lifting.app.core.database.model.CountWithDateEntity
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.ExerciseLogEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import com.lifting.app.core.database.model.ExerciseLogEntryEntity.Companion.calculateTotalVolume
import com.lifting.app.core.database.model.ExerciseLogEntryEntity.Companion.getTotalPRs
import com.lifting.app.core.database.model.ExerciseSetGroupNoteEntity
import com.lifting.app.core.database.model.ExerciseWithInfoResource
import com.lifting.app.core.database.model.ExerciseWorkoutJunction
import com.lifting.app.core.database.model.LogEntriesWithExerciseResource
import com.lifting.app.core.database.model.LogEntriesWithExtraInfoJunction
import com.lifting.app.core.database.model.LogEntriesWithWorkoutResource
import com.lifting.app.core.database.model.PlateEntity
import com.lifting.app.core.database.model.TemplateWithWorkoutResource
import com.lifting.app.core.database.model.WorkoutEntity
import com.lifting.app.core.database.model.WorkoutTemplateEntity
import com.lifting.app.core.database.model.WorkoutWithExtraInfoResource
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseLog
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogEntriesWithExtraInfo
import com.lifting.app.core.model.LogEntriesWithWorkout
import com.lifting.app.core.model.Plate
import com.lifting.app.core.model.TemplateWithWorkout
import com.lifting.app.core.model.Workout
import com.lifting.app.core.model.WorkoutTemplate
import com.lifting.app.core.model.WorkoutWithExtraInfo

/**
 * Created by bedirhansaricayir on 21.08.2024
 */
object Mapper {
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

    fun WorkoutTemplateEntity.toDomain() = with(this) {
        WorkoutTemplate(
            id = id,
            isHidden = isHidden,
            workoutId = workoutId,
            lastPerformedAt = lastPerformedAt,
            createdAt = createdAt,
            updatedAt = updatedAt,
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

    @JvmName("toDomainWorkoutEntity")
    fun List<WorkoutEntity>.toDomain(): List<Workout> = this.map { it.toDomain() }

    fun WorkoutEntity.toDomain(): Workout = with(this) {
        Workout(
            id = id,
            name = name,
            note = note,
            inProgress = inProgress,
            isHidden = isHidden,
            startAt = startAt,
            completedAt = completedAt,
            personalRecords = personalRecords,
            createdAt = createdAt,
            updatedAt = updatedAt,
            duration = getDuration()
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

    fun ExerciseLogEntity.toDomain() = with(this) {
        ExerciseLog(
            id = id,
            workoutId = workoutId,
            createdAt = createdAt,
            updatedAt = updatedAt,
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

    fun ExerciseSetGroupNoteEntity.toDomain() = with(this) {
        ExerciseSetGroupNote(
            id = id,
            note = note,
            exerciseWorkoutJunctionId = exerciseWorkoutJunctionId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun Exercise.toEntity(): ExerciseEntity = with(this) {
        ExerciseEntity(
            exerciseId = exerciseId,
            name = name,
            notes = notes,
            equipmentId = equipmentId,
            primaryMuscleTag = primaryMuscleTag,
            secondaryMuscleTag = secondaryMuscleTag,
            category = category,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun ExerciseEntity.toDomain() = with(this) {
        Exercise(
            exerciseId = exerciseId,
            name = name,
            notes = notes,
            equipmentId = equipmentId,
            primaryMuscleTag = primaryMuscleTag,
            secondaryMuscleTag = secondaryMuscleTag,
            category = category,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    @JvmName("toDomainExerciseEntity")
    fun List<ExerciseEntity>.toDomain(): List<Exercise> = this.map { it.toDomain() }

    fun LogEntriesWithWorkoutResource.toDomain() = with(this) {
        LogEntriesWithWorkout(
            junction = junction.toDomain(),
            workout = workout.toDomain(),
            logEntries = logEntries.map { it.toDomain() },
            notes = notes?.map { it.toDomain() }
        )
    }

    @JvmName("toDomainLogEntriesWithWorkoutResource")
    fun List<LogEntriesWithWorkoutResource>.toDomain(): List<LogEntriesWithWorkout> = this.map { it.toDomain() }

    fun ExerciseWorkoutJunction.toDomain() = with(this) {
        ExerciseWorkoutJunc(
            id = id,
            supersetId = supersetId,
            barbellId = barbellId,
            exerciseId = exerciseId,
            workoutId = workoutId
        )
    }

    fun ExerciseWithInfoResource.toDomain() = with(this) {
        ExerciseWithInfo(
            exercise = exercise.toDomain(),
            logsCount = logsCount
        )
    }

    @JvmName("toDomainExerciseWithInfoResource")
    fun List<ExerciseWithInfoResource>.toDomain(): List<ExerciseWithInfo> = this.map { it.toDomain() }

    fun LogEntriesWithExercise.toEntity(): LogEntriesWithExerciseResource = with(this) {
        LogEntriesWithExerciseResource(
            junction = junction.toEntity(),
            exercise = exercise.toEntity(),
            logEntries = logEntries.map { it.toEntity() },
            notes = notes?.map { it.toEntity() }
        )
    }

    @JvmName("toDomainLogEntriesWithExerciseResource")
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
            logEntries = logEntries.map { it.toDomain() }
        )
    }

    fun ExerciseLogEntryEntity.toDomain() = with(this) {
        ExerciseLogEntry(
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
            weightUnit = weight_unit,
            distanceUnit = distance_unit,
            personalRecords = personalRecords,
            createdAt = createdAt,
            updatedAt = updatedAt
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

    @JvmName("toDomainCountWithDateEntity")
    fun List<CountWithDateEntity>.toDomain(): List<CountWithDate> = this.map { it.toDomain() }
    fun CountWithDateEntity.toDomain(): CountWithDate =
        CountWithDate(
            date = date,
            count = count
        )

    @JvmName("toDomainPlateEntity")
    fun List<PlateEntity>.toDomain(): List<Plate> = this.map { it.toDomain() }
    fun PlateEntity.toDomain(): Plate = with(this) {
        Plate(
            id = id,
            weight = weight,
            forWeightUnit = forWeightUnit,
            isActive = isActive,
            color = color,
            colorValueType = colorValueType,
            height = height,
            width = width,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun Plate.toEntity(): PlateEntity = with(this) {
        PlateEntity(
            id = id,
            weight = weight,
            forWeightUnit = forWeightUnit,
            isActive = isActive,
            color = color,
            colorValueType = colorValueType,
            height = height,
            width = width,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    @JvmName("toDomainBarbellEntity")
    fun List<BarbellEntity>.toDomain(): List<Barbell> = this.map { it.toDomain() }
    fun BarbellEntity.toDomain(): Barbell = with(this) {
        Barbell(
            id = id,
            name = name,
            weightKg = weightKg,
            weightLbs = weightLbs,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun Barbell.toEntity(): BarbellEntity = with(this) {
        BarbellEntity(
            id = id,
            name = name,
            weightKg = weightKg,
            weightLbs = weightLbs,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun TemplateWithWorkoutResource.toDomain() = with(this) {
        TemplateWithWorkout(
            template = template.toDomain(),
            workout = workout.toDomain(),
            junctions = exerciseWorkoutJunctions.map { it.toDomain() }

        )
    }

    fun List<TemplateWithWorkoutResource>.toDomain() = this.map { it.toDomain() }
}