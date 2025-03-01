package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 07.02.2025
 */
data class TemplateWithWorkout(
    val template: WorkoutTemplate,
    val workout: Workout,
    val junctions: List<ExerciseWorkoutJunc>
)
