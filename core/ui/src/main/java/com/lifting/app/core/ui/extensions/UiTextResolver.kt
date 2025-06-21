package com.lifting.app.core.ui.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.toDurationHms
import com.lifting.app.core.model.BarbellType
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.Equipment
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.Muscle
import com.lifting.app.core.model.TimePeriod
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.R

/**
 * Created by bedirhansaricayir on 16.05.2025
 */

fun ExerciseCategory?.getReadableName(): Int {
    return when (this) {
        ExerciseCategory.ASSISTED_BODYWEIGHT -> R.string.assisted_bodyweight_readable_text
        ExerciseCategory.BODYWEIGHT_REPS -> R.string.bodyweight_reps_readable_text
        ExerciseCategory.DISTANCE_AND_DURATION -> R.string.distance_and_duration_readable_text
        ExerciseCategory.DURATION -> R.string.duration_readable_text
        ExerciseCategory.WEIGHT_AND_DISTANCE -> R.string.weight_and_distance_readable_text
        ExerciseCategory.WEIGHT_AND_DURATION -> R.string.weight_and_duration_readable_text
        ExerciseCategory.WEIGHT_AND_REPS -> R.string.weight_and_reps_readable_text
        ExerciseCategory.WEIGHTED_BODYWEIGHT -> R.string.weighted_bodyweight_readable_text
        else -> R.string.unknown_category_readable_text
    }
}

fun ExerciseCategory?.getExamplesString(): Int {
    return when (this) {
        ExerciseCategory.ASSISTED_BODYWEIGHT -> R.string.assisted_bodyweight_examples_text
        ExerciseCategory.BODYWEIGHT_REPS -> R.string.bodyweight_reps_examples_text
        ExerciseCategory.DISTANCE_AND_DURATION -> R.string.distance_and_duration_examples_text
        ExerciseCategory.DURATION -> R.string.duration_examples_text
        ExerciseCategory.WEIGHT_AND_DISTANCE -> R.string.weight_and_distance_examples_text
        ExerciseCategory.WEIGHT_AND_DURATION -> R.string.weight_and_duration_examples_text
        ExerciseCategory.WEIGHT_AND_REPS -> R.string.weight_and_reps_examples_text
        ExerciseCategory.WEIGHTED_BODYWEIGHT -> R.string.weighted_bodyweight_examples_text
        else -> R.string.unknown_category_examples_text
    }
}

/**
 * @param case 0=Pascal Case, 1=Upper Case, 2=Lower Case
 */
fun WeightUnit.toLocalizedString(case: Int = 2): Int = when (this) {
    WeightUnit.Kg -> when (case) {
        1 -> R.string.kg_uppercase
        2 -> R.string.kg_lowercase
        else -> R.string.kg
    }

    WeightUnit.Lbs -> when (case) {
        1 -> R.string.lbs_uppercase
        2 -> R.string.lbs_lowercase
        else -> R.string.lbs
    }
}

/**
 * @param case 0=Pascal Case, 1=Upper Case, 2=Lower Case
 */
fun DistanceUnit.toLocalizedString(case: Int = 2): Int = when (this) {
    DistanceUnit.Km -> when (case) {
        1 -> R.string.km_uppercase
        2 -> R.string.km_lowercase
        else -> R.string.km
    }

    DistanceUnit.Miles -> when (case) {
        1 -> R.string.miles_uppercase
        2 -> R.string.miles_lowercase
        else -> R.string.miles
    }
}

fun String.toLocalizedBarbellName(): Int = when (this) {
    BarbellType.Olympic.name -> R.string.olympic_barbell
    BarbellType.WomenOlympic.name -> R.string.women_olympic_barbell
    BarbellType.Training.name -> R.string.training_barbell
    BarbellType.Technique.name -> R.string.technique_barbell
    BarbellType.EZCurl.name -> R.string.ez_curl_barbell
    BarbellType.Trap.name -> R.string.trap_barbell
    BarbellType.SafetySquat.name -> R.string.safety_squat_barbell
    BarbellType.Swiss.name -> R.string.swiss_barbell
    else -> R.string.olympic_barbell
}

@Composable
fun Long.toLocalizedDuration(): String {
    val context = LocalContext.current
    val (h, m, s) = this.toDurationHms()

    return listOfNotNull(
        h.takeIf { it > 0 }?.let {
            context.resources.getQuantityString(R.plurals.duration_hours, it.toInt(), it)
        },
        m.takeIf { it > 0 }?.let {
            context.resources.getQuantityString(R.plurals.duration_minutes, it.toInt(), it)
        },
        s.takeIf { it > 0 && h == 0L && m == 0L }?.let {
            context.resources.getQuantityString(R.plurals.duration_seconds, it.toInt(), it)
        }
    ).joinToString(" ")
}

fun TimePeriod.toCreateWorkoutNameByPeriod(context: Context): String = when (this) {
    TimePeriod.MORNING -> context.getString(R.string.morning_period_workout_name)
    TimePeriod.AFTERNOON -> context.getString(R.string.afternoon_period_workout_name)
    TimePeriod.EVENING -> context.getString(R.string.evening_period_workout_name)
    TimePeriod.NIGHT -> context.getString(R.string.night_period_workout_name)
}

fun Equipment.toLocalizedString(): Int = when (this) {
    Equipment.BARBELL -> R.string.equipment_barbell
    Equipment.BAND -> R.string.equipment_band
    Equipment.BODYWEIGHT -> R.string.equipment_bodyweight
    Equipment.CABLE -> R.string.equipment_cable
    Equipment.DUMBBELL -> R.string.equipment_dumbbell
    Equipment.MACHINE -> R.string.equipment_machine
    Equipment.KETTLEBELL -> R.string.equipment_kettlebell
    Equipment.SLAM_BALL -> R.string.equipment_slam_ball
    Equipment.OTHER -> R.string.equipment_other
}

fun String.toLocalizedMuscleName(): Int = when (Muscle.fromTag(this)) {
    Muscle.ABS -> R.string.abs
    Muscle.ADDUCTORS -> R.string.adductors
    Muscle.BACK -> R.string.back
    Muscle.BICEPS -> R.string.biceps
    Muscle.CALVES -> R.string.calves
    Muscle.CARDIO -> R.string.cardio
    Muscle.CHEST -> R.string.chest
    Muscle.CORE -> R.string.core
    Muscle.FOREARMS -> R.string.forearms
    Muscle.FULL_BODY -> R.string.full_body
    Muscle.GLUTES -> R.string.glutes
    Muscle.HAMSTRINGS -> R.string.hamstrings
    Muscle.LATS -> R.string.lats
    Muscle.LOWER_BACK -> R.string.lower_back
    Muscle.WEIGHTLIFTING -> R.string.olympic_weightlifting
    Muscle.QUADRICEPS -> R.string.quadriceps
    Muscle.SHOULDERS -> R.string.shoulders
    Muscle.TRAPS -> R.string.traps
    Muscle.TRICEPS -> R.string.triceps
    Muscle.UPPER_BACK -> R.string.upper_back
    null -> R.string.unknown_muscle
}

@Composable
fun getExerciseNoteString(exerciseId: String): String {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(
        "${exerciseId}_notes",
        "string",
        context.packageName
    )

    return if (resId != 0) {
        stringResource(id = resId)
    } else {
        "Fallback"
    }
}
