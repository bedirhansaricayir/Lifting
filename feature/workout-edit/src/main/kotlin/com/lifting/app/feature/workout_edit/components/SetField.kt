package com.lifting.app.feature.workout_edit.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.lifting.app.core.common.extensions.orEmpty
import com.lifting.app.core.common.extensions.toKg
import com.lifting.app.core.common.extensions.toRpe
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.SetFieldValueType
import com.lifting.app.core.ui.components.LiftingSetInputField
import com.lifting.app.core.ui.R


/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Composable
internal fun RowScope.SetFieldTitleColumns(
    exercise: Exercise,
) {
    Text(
        text = stringResource(id = R.string.set),
        style = LiftingTheme.typography.caption,
        color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(0.5f)
    )

    exercise.category?.fields.orEmpty().forEach { field ->
        Text(
            text = stringResource(
                id = when (field) {
                    SetFieldValueType.WEIGHT,
                    SetFieldValueType.ADDITIONAL_WEIGHT,
                    SetFieldValueType.ASSISTED_WEIGHT -> R.string.kg

                    SetFieldValueType.REPS -> R.string.reps
                    SetFieldValueType.RPE -> R.string.rpe
                    SetFieldValueType.DISTANCE -> R.string.km
                    SetFieldValueType.DURATION -> R.string.duration
                }
            ),
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(
                when (field) {
                    SetFieldValueType.RPE -> .75f
                    else -> 1.25f
                }
            )
        )
    }
}

@Composable
internal fun RowScope.SetFieldColumns(
    contentColor: Color,
    bgColor: Color,
    exercise: Exercise,
    barbell: Barbell?,
    exerciseLogEntry: ExerciseLogEntry,
    onWeightChange: (ExerciseLogEntry, Double?) -> Unit,
    onDistanceChange: (ExerciseLogEntry, Double?) -> Unit,
    onRepsChange: (ExerciseLogEntry, Int?) -> Unit,
    onDurationChange: (ExerciseLogEntry, Long?) -> Unit,
    onRpeChange: (ExerciseLogEntry, Float?) -> Unit,
) {
    exercise.category?.fields.orEmpty().forEach { field ->
        SetField(
            contentColor = contentColor,
            bgColor = bgColor,
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
internal fun RowScope.SetFieldWeight(
    contentColor: Color,
    bgColor: Color,
    weight: Double?,
    onWeightChange: (Double?) -> Unit,
) {
    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue = changedValue.takeIf(String::isNotBlank)?.trim()?.toDoubleOrNull()
        onWeightChange(newValue)
    }

    LiftingSetInputField(
        value = weight.toKg(),
        onValueChange = onValueChanged,
        contentColor = contentColor,
        containerColor = bgColor,
    )
}


@Composable
internal fun RowScope.SetFieldReps(
    contentColor: Color,
    bgColor: Color,
    reps: Int?,
    onRepsChange: (Int?) -> Unit,
) {
    val onValueChanged: (String) -> Unit = { changedValue ->
        val newValue = changedValue.takeIf(String::isNotBlank)?.trim()?.toIntOrNull()
        onRepsChange(newValue)
    }

    LiftingSetInputField(
        value = reps.orEmpty(),
        onValueChange = onValueChanged,
        contentColor = contentColor,
        containerColor = bgColor,
    )
}


@Composable
internal fun RowScope.SetFieldRpe(
    contentColor: Color,
    bgColor: Color,
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
        contentColor = contentColor,
        containerColor = bgColor,
    )
}

@Composable
internal fun RowScope.SetField(
    contentColor: Color,
    bgColor: Color,
    barbell: Barbell?,
    entry: ExerciseLogEntry,
    fieldType: SetFieldValueType,
    onWeightChange: (ExerciseLogEntry, Double?) -> Unit,
    onDistanceChange: (ExerciseLogEntry, Double?) -> Unit,
    onRepsChange: (ExerciseLogEntry, Int?) -> Unit,
    onDurationChange: (ExerciseLogEntry, Long?) -> Unit,
    onRpeChange: (ExerciseLogEntry, Float?) -> Unit,
) {
    when (fieldType) {
        SetFieldValueType.WEIGHT,
        SetFieldValueType.ADDITIONAL_WEIGHT,
        SetFieldValueType.ASSISTED_WEIGHT -> SetFieldWeight(
            contentColor = contentColor,
            bgColor = bgColor,
            weight = entry.weight,
            onWeightChange = {
                onWeightChange(entry, it)
            },
        )

        SetFieldValueType.REPS -> SetFieldReps(
            contentColor = contentColor,
            bgColor = bgColor,
            reps = entry.reps,
            onRepsChange = {
                onRepsChange(entry, it)
            }
        )

        SetFieldValueType.RPE -> SetFieldRpe(
            contentColor = contentColor,
            bgColor = bgColor,
            rpe = entry.rpe,
            onRpeChange = {
                onRpeChange(entry, it)
            }
        )

        else -> Unit
    }
}
