package com.lifting.app.feature.workout_editor

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.lifting.app.core.common.extensions.orEmpty
import com.lifting.app.core.common.extensions.replaceWithDot
import com.lifting.app.core.common.extensions.toKgIfLbs
import com.lifting.app.core.common.extensions.toKmIfMiles
import com.lifting.app.core.common.extensions.toMMSSFromString
import com.lifting.app.core.common.extensions.toMillisFromMMSS
import com.lifting.app.core.common.extensions.toRpe
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.keyboard.model.LiftingKeyboardType
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.SetFieldValueType
import com.lifting.app.core.model.isLbs
import com.lifting.app.core.model.isMiles
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.common.LocalAppSettings
import com.lifting.app.core.ui.common.preferencesDistanceUnitString
import com.lifting.app.core.ui.common.preferencesWeightUnitString
import com.lifting.app.core.ui.common.toDistanceUnitPreferencesString
import com.lifting.app.core.ui.common.toWeightUnitPreferencesString
import com.lifting.app.feature.workout_editor.components.LiftingSetInputField


/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Composable
internal fun RowScope.SetFieldTitleColumns(
    exercise: Exercise,
) {
    Text(
        text = stringResource(id = R.string.set_uppercase),
        style = LiftingTheme.typography.caption,
        color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(0.5f)
    )

    exercise.category?.fields.orEmpty().forEach { field ->
        Text(
            text = when (field) {
                SetFieldValueType.WEIGHT,
                SetFieldValueType.ADDITIONAL_WEIGHT,
                SetFieldValueType.ASSISTED_WEIGHT -> preferencesWeightUnitString(case = 1)

                SetFieldValueType.REPS -> stringResource(R.string.reps)
                SetFieldValueType.RPE -> stringResource(R.string.rpe)
                SetFieldValueType.DISTANCE -> preferencesDistanceUnitString(case = 1)
                SetFieldValueType.DURATION -> stringResource(R.string.duration)
            },
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(
                when (field) {
                    SetFieldValueType.RPE -> .75f
                    SetFieldValueType.DURATION -> 3.25f
                    else -> 1.25f
                }
            )
        )
    }
}

@Composable
internal fun RowScope.SetFieldColumns(
    exercise: Exercise,
    barbell: Barbell?,
    exerciseLogEntry: ExerciseLogEntry,
    onWeightChange: (ExerciseLogEntry, Double?) -> Unit,
    onDistanceChange: (ExerciseLogEntry, Double?) -> Unit,
    onRepsChange: (ExerciseLogEntry, Int?) -> Unit,
    onDurationChange: (ExerciseLogEntry, Long?) -> Unit,
    onRpeChange: (ExerciseLogEntry, Float?) -> Unit
) {
    exercise.category?.fields.orEmpty().forEach { field ->
        SetField(
            barbell = barbell,
            entry = exerciseLogEntry,
            fieldType = field,
            onWeightChange = onWeightChange,
            onDistanceChange = onDistanceChange,
            onRepsChange = onRepsChange,
            onDurationChange = onDurationChange,
            onRpeChange = onRpeChange,
        )
    }
}

@Composable
internal fun RowScope.SetField(
    barbell: Barbell?,
    entry: ExerciseLogEntry,
    fieldType: SetFieldValueType,
    onWeightChange: (ExerciseLogEntry, Double?) -> Unit,
    onDistanceChange: (ExerciseLogEntry, Double?) -> Unit,
    onRepsChange: (ExerciseLogEntry, Int?) -> Unit,
    onDurationChange: (ExerciseLogEntry, Long?) -> Unit,
    onRpeChange: (ExerciseLogEntry, Float?) -> Unit
) {
    when (fieldType) {
        SetFieldValueType.WEIGHT,
        SetFieldValueType.ADDITIONAL_WEIGHT,
        SetFieldValueType.ASSISTED_WEIGHT -> SetFieldWeight(
            weight = entry.weight,
            barbell = barbell,
            onWeightChange = {
                onWeightChange(entry, it)
            }
        )

        SetFieldValueType.REPS -> SetFieldReps(
            reps = entry.reps,
            onRepsChange = {
                onRepsChange(entry, it)
            }
        )

        SetFieldValueType.RPE -> SetFieldRpe(
            rpe = entry.rpe,
            onRpeChange = {
                onRpeChange(entry, it)
            }
        )

        SetFieldValueType.DISTANCE -> SetFieldDistance(
            distance = entry.distance,
            onDistanceChange = {
                onDistanceChange(entry, it)
            }
        )

        SetFieldValueType.DURATION -> SetFieldDuration(
            duration = entry.timeRecorded,
            onDurationChange = {
                onDurationChange(entry, it)
            }
        )
    }
}

@Composable
internal fun RowScope.SetFieldWeight(
    weight: Double?,
    barbell: Barbell?,
    onWeightChange: (Double?) -> Unit
) {
    val weightUnit = LocalAppSettings.current.weightUnit

    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue =
            changedValue.takeIf(String::isNotBlank)?.trim()?.replaceWithDot()?.toDoubleOrNull()
                .toKgIfLbs(weightUnit.isLbs())
        onWeightChange(newValue)
    }

    LiftingSetInputField(
        value = weight.toWeightUnitPreferencesString(),
        onValueChange = onValueChanged,
        keyboardType = LiftingKeyboardType.Weight(barbell)
    )
}

@Composable
internal fun RowScope.SetFieldReps(
    reps: Int?,
    onRepsChange: (Int?) -> Unit
) {
    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue = changedValue.takeIf(String::isNotBlank)?.trim()?.toIntOrNull()
        onRepsChange(newValue)
    }

    LiftingSetInputField(
        value = reps.orEmpty(),
        onValueChange = onValueChanged,
        keyboardType = LiftingKeyboardType.Reps
    )
}

@Composable
internal fun RowScope.SetFieldRpe(
    rpe: Float?,
    onRpeChange: (Float?) -> Unit,
) {
    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue = changedValue.toFloatOrNull()
        onRpeChange(newValue)
    }

    LiftingSetInputField(
        layoutWeight = 0.75f,
        value = rpe.toRpe(),
        onValueChange = onValueChanged,
        keyboardType = LiftingKeyboardType.Rpe,
    )
}

@Composable
internal fun RowScope.SetFieldDistance(
    distance: Double?,
    onDistanceChange: (Double?) -> Unit
) {
    val distanceUnit = LocalAppSettings.current.distanceUnit

    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue =
            changedValue.takeIf(String::isNotBlank)?.trim()?.replaceWithDot()?.toDoubleOrNull()
                .toKmIfMiles(distanceUnit.isMiles())
        onDistanceChange(newValue)
    }

    LiftingSetInputField(
        value = distance.toDistanceUnitPreferencesString(),
        onValueChange = onValueChanged,
        keyboardType = LiftingKeyboardType.Distance
    )
}

@Composable
internal fun RowScope.SetFieldDuration(
    duration: Long?,
    onDurationChange: (Long?) -> Unit
) {
    val onValueChanged: (String) -> Unit = { changedValue ->
        onDurationChange(changedValue.toMillisFromMMSS())
    }

    LiftingSetInputField(
        value = duration?.toMMSSFromString() ?: "",
        layoutWeight = 3.25f,
        onValueChange = onValueChanged,
        keyboardType = LiftingKeyboardType.Time,
    )
}