package com.lifting.app.feature.workout_edit.components

import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.SetFieldValueType

/**
 * Created by bedirhansaricayir on 09.02.2025
 */
internal object WorkoutEditorUtils {
    fun isValidSet(logEntry: ExerciseLogEntry, exerciseCategory: ExerciseCategory?): Boolean {
        if (exerciseCategory == null) return true

        val fields: List<Any?> = exerciseCategory.fields.map {
            when (it) {
                SetFieldValueType.WEIGHT,
                SetFieldValueType.ADDITIONAL_WEIGHT,
                SetFieldValueType.ASSISTED_WEIGHT -> logEntry.weight
                SetFieldValueType.REPS -> logEntry.reps
                SetFieldValueType.DISTANCE -> logEntry.distance
                SetFieldValueType.DURATION -> logEntry.timeRecorded
                SetFieldValueType.RPE -> true
            }
        }

        return !fields.any {
            it == null
        }
    }
}