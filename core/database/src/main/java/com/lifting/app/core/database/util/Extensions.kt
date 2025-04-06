package com.lifting.app.core.database.util

import com.lifting.app.core.database.model.ExerciseLogEntryEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

fun List<ExerciseLogEntryEntity>.calculateTotalVolume() = this.sumOf { ((it.weight ?: 0.0) * (it.reps ?: 0).toDouble()) }

fun List<ExerciseLogEntryEntity>.getTotalPRs(workoutPrs: Int? = null) = this.sumOf { it.personalRecords?.size ?: 0 } + (workoutPrs ?: 0)

