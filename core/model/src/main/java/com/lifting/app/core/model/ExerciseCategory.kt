package com.lifting.app.core.model

import com.lifting.app.core.model.ExerciseCategory.entries
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Serializable
enum class ExerciseCategory(val tag: String, val fields: List<SetFieldValueType>) {
    @SerialName("weight_and_reps")
    WEIGHT_AND_REPS(
        tag = "weight_and_reps",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.REPS, SetFieldValueType.RPE)
    ),

    @SerialName("bodyweight_reps")
    BODYWEIGHT_REPS(
        tag = "bodyweight_reps",
        fields = listOf(SetFieldValueType.REPS, SetFieldValueType.RPE)
    ),

    @SerialName("weighted_bodyweight")
    WEIGHTED_BODYWEIGHT(
        tag = "weighted_bodyweight",
        fields = listOf(
            SetFieldValueType.ADDITIONAL_WEIGHT,
            SetFieldValueType.REPS,
            SetFieldValueType.RPE
        )
    ),

    @SerialName("assisted_bodyweight")
    ASSISTED_BODYWEIGHT(
        tag = "assisted_bodyweight",
        fields = listOf(
            SetFieldValueType.ASSISTED_WEIGHT,
            SetFieldValueType.REPS,
            SetFieldValueType.RPE
        )
    ),

    @SerialName("duration")
    DURATION(
        tag = "duration",
        fields = listOf(SetFieldValueType.DURATION)
    ),

    @SerialName("distance_and_duration")
    DISTANCE_AND_DURATION(
        tag = "distance_and_duration",
        fields = listOf(SetFieldValueType.DISTANCE, SetFieldValueType.DURATION)
    ),

    @SerialName("weight_and_distance")
    WEIGHT_AND_DISTANCE(
        tag = "weight_and_distance",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.DISTANCE)
    ),

    @SerialName("weight_and_duration")
    WEIGHT_AND_DURATION(
        tag = "weight_and_duration",
        fields = listOf(SetFieldValueType.WEIGHT, SetFieldValueType.DURATION)
    );

    companion object {
        fun allCategories(): List<ExerciseCategory> = entries
        fun getExerciseCategoryByTag(tag: String): ExerciseCategory = entries.find { it.tag == tag } ?: WEIGHT_AND_REPS
    }
}