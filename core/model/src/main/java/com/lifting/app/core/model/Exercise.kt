package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class Exercise(
    val exerciseId: String,
    val name: String?,
    val notes: String?,
    val equipmentId: String? = null,
    val primaryMuscleTag: String?,
    val secondaryMuscleTag: String?,
    val category: ExerciseCategory?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)