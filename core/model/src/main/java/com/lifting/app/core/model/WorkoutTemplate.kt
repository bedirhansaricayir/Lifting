package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 07.02.2025
 */
data class WorkoutTemplate(
    val id: String,
    val isHidden: Boolean?,
    val workoutId: String?,
    val lastPerformedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
