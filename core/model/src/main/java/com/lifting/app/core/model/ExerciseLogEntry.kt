package com.lifting.app.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Parcelize
data class ExerciseLogEntry(
    val entryId: String,
    val logId: String?,
    val junctionId: String?,
    val setNumber: Int?,
    val setType: LogSetType?,
    val weight: Double?,
    val reps: Int?,
    val rpe: Float?,
    val completed: Boolean,
    val timeRecorded: Long?,
    val distance: Double?,
    val weightUnit: String?,
    val distanceUnit: String?,
    val personalRecords: List<PersonalRecord>?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) : Parcelable

fun List<ExerciseLogEntry>.calculateTotalVolume(): Double {
    return this.sumOf { entry -> (entry.weight ?: 0.0) * (entry.reps ?: 0) }
}