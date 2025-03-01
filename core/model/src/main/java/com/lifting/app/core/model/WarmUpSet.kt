package com.lifting.app.core.model

import java.util.UUID

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

data class WarmUpSet(
    var id: String,
    var weight: Double? = null,
    var reps: Int? = null,
    var weightPercentage: Int? = null,
    val formula: String? = null
) {
    fun findFormula(): String {
        return formula ?: "Bar x 1"
    }

    companion object {
        fun refreshWarmupSetsWithNewWorkWeight(
            workSetWeight: Double?,
            lastWarmupSets: List<WarmUpSet>
        ): List<WarmUpSet> {
            return lastWarmupSets.map { set ->
                if (set.findFormula()
                        .contains("Bar") || set.weightPercentage == null || workSetWeight == null
                ) {
                    set
                } else {
                    set.copy(
                        weight = workSetWeight * set.weightPercentage!! / 100
                    )
                }
            }
        }
    }
}

fun WarmUpSet.toExerciseLogEntry(): ExerciseLogEntry = with(this) {
    ExerciseLogEntry(
        entryId = UUID.randomUUID().toString(),
        weight = weight,
        reps = reps,
        setType = LogSetType.WARM_UP,
        completed = false,
        createdAt = null,
        distanceUnit = null,
        distance = null,
        updatedAt = null,
        logId = null,
        personalRecords = null,
        timeRecorded = null,
        junctionId = null,
        rpe = null,
        setNumber = null,
        weightUnit = null
    )
}

fun List<WarmUpSet>.toExerciseLogEntries(): List<ExerciseLogEntry> = with(this) {
    this.map { it.toExerciseLogEntry() }
}
