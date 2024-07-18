package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class ExerciseWithMuscle(
    val exercise: Exercise,
    val primaryMuscle: Muscle?,
    val secondaryMuscle: Muscle?,
    val junctions: List<ExerciseWorkoutJunc>
)
