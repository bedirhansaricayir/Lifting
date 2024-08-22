package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 15.07.2024
 */


enum class SetFieldValueType {
    WEIGHT,
    ADDITIONAL_WEIGHT,
    ASSISTED_WEIGHT,
    REPS,
    RPE,
    DISTANCE,
    DURATION;
}

sealed class ExerciseCategory(open val tag: String, open val fields: List<SetFieldValueType>, open val readableName: String) {
    data object WeightAndReps : ExerciseCategory(
        tag = "weight_and_reps",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.REPS, SetFieldValueType.RPE),
        readableName = "Weight & Reps"
    )

    data object BodyweightReps : ExerciseCategory(
        tag = "bodyweight_reps",
        fields = listOf(SetFieldValueType.REPS, SetFieldValueType.RPE),
        readableName = "Bodyweight Reps"
    )

    data object WeightedBodyweight : ExerciseCategory(
        tag = "weighted_bodyweight",
        fields = listOf(
            SetFieldValueType.ADDITIONAL_WEIGHT,
            SetFieldValueType.REPS,
            SetFieldValueType.RPE
        ),
        readableName = "Weighted Bodyweight"
    )

    data object AssistedBodyweight : ExerciseCategory(
        tag = "assisted_bodyweight",
        fields = listOf(
            SetFieldValueType.ASSISTED_WEIGHT,
            SetFieldValueType.REPS,
            SetFieldValueType.RPE
        ),
        readableName = "Assisted Bodyweight"
    )

    data object Duration : ExerciseCategory(
        tag = "duration",
        fields = listOf(SetFieldValueType.DURATION),
        readableName = "Duration"
    )

    data object DistanceAndDuration : ExerciseCategory(
        tag = "distance_and_duration",
        fields = listOf(SetFieldValueType.DISTANCE, SetFieldValueType.DURATION),
        readableName = "Distance & Duration"
    )

    data object WeightAndDistance : ExerciseCategory(
        tag = "weight_and_distance",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.DISTANCE),
        readableName = "Weight & Distance"
    )

    data object WeightAndDuration : ExerciseCategory(
        tag = "weight_and_duration",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.DURATION),
        readableName = "Weight & Duration"
    )

    data class Unknown(
        override val tag: String,
        override val fields: List<SetFieldValueType>
    ) : ExerciseCategory(
        tag = tag,
        fields = fields,
        readableName = "Unknown"
    )
}

val allExerciseCategories = listOf(
    ExerciseCategory.WeightAndReps,
    ExerciseCategory.BodyweightReps,
    ExerciseCategory.WeightedBodyweight,
    ExerciseCategory.AssistedBodyweight,
    ExerciseCategory.Duration,
    ExerciseCategory.DistanceAndDuration,
    ExerciseCategory.WeightAndDistance,
    ExerciseCategory.WeightAndDuration,
)

fun String?.parseToExerciseCategory(): ExerciseCategory {
    return allExerciseCategories.find { it.tag == this } ?: ExerciseCategory.Unknown(
        tag = this ?: "",
        fields = emptyList()
    )
}