package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

data class WorkoutWithExtraInfo(
    val workout: Workout?,
    val totalVolume: Double?,
    val totalExercises: Int?,
    val totalPRs: Int?,
)