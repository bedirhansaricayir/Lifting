package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

data class ExerciseSetGroupNote(
    val id: String,
    val note: String?,
    val exerciseWorkoutJunctionId: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)