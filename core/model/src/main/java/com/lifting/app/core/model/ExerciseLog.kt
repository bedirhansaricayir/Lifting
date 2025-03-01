package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 08.02.2025
 */
data class ExerciseLog(
    val id: String,
    val workoutId: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)
