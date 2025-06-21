package com.lifting.app.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.toMMSSFromString
import com.lifting.app.core.common.extensions.toRpe
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.LogSetType
import com.lifting.app.core.model.SetFieldValueType
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.common.preferencesDistanceUnitString
import com.lifting.app.core.ui.common.preferencesWeightUnitString
import com.lifting.app.core.ui.common.toDistanceUnitPreferencesString
import com.lifting.app.core.ui.common.toWeightUnitPreferencesString
import com.lifting.app.core.ui.extensions.alphaClickable
import com.lifting.app.core.ui.extensions.darkerOrLighter
import com.lifting.app.core.ui.extensions.randomColorById

/**
 * Created by bedirhansaricayir on 22.04.2025
 */

@Composable
fun SessionExerciseCardItem(
    title: String?,
    entries: List<ExerciseLogEntry>,
    modifier: Modifier = Modifier,
    shape: Shape = LiftingTheme.shapes.medium,
    onClick: (() -> Unit)? = null,
    subtitle: String? = null,
    supersetId: Int? = null,
    exerciseCategory: ExerciseCategory?,
    notes: List<ExerciseSetGroupNote>? = null
) {
    val sortedEntries by remember(key1 = entries) {
        mutableStateOf(entries.sortedWith(ExerciseLogEntryComparator))
    }

    fun getRevisedSetNumbers(): List<Pair<String, Color?>> {
        var counter = 0
        val newPairs = sortedEntries.map {
            when (it.setType ?: LogSetType.NORMAL) {
                LogSetType.NORMAL -> {
                    counter++
                    Pair(counter.toString(), null)
                }

                LogSetType.WARM_UP -> Pair("W", Color.Yellow)
                LogSetType.DROP_SET -> {
                    counter++
                    Pair("D", Color.Magenta)
                }

                LogSetType.FAILURE -> {
                    counter++
                    Pair("F", Color.Red)
                }
            }
        }

        return newPairs
    }

    val revisedSetsTexts by remember(key1 = sortedEntries) {
        mutableStateOf(getRevisedSetNumbers())
    }

    LiftingCard(
        modifier = modifier,
        shape = shape,
        onClick = { onClick?.invoke() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            title?.let { title ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large)
                ) {
                    Text(
                        text = title, style = LiftingTheme.typography.subtitle1,
                        color = LiftingTheme.colors.onBackground
                    )

                    supersetId?.let { id ->
                        val supersetColor by remember(supersetId) {
                            mutableStateOf(Color.randomColorById(supersetId))
                        }
                        Box(
                            modifier = Modifier
                                .background(supersetColor.copy(.5f), CircleShape)
                                .padding(LiftingTheme.dimensions.small),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.superset),
                                style = LiftingTheme.typography.button,
                                color = supersetColor.darkerOrLighter(0.8f)
                            )
                        }
                    }
                }
            }

            subtitle?.let {
                Text(
                    text = it, style = LiftingTheme.typography.subtitle2,
                    color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
                )
            }

            notes?.let { noteList ->
                noteList.forEach { note ->
                    note.note?.let { noteText ->
                        var isExpanded by rememberSaveable { mutableStateOf(false) }

                        Text(
                            text = noteText, style = LiftingTheme.typography.subtitle2,
                            color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
                            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                            overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                            modifier = Modifier
                                .alphaClickable {
                                    isExpanded = !isExpanded
                                }
                                .animateContentSize(),
                        )
                    }
                }
            }

            if (sortedEntries.isNotEmpty()) {
                sortedEntries.indices.forEach { i ->
                    val entry = sortedEntries[i]
                    SessionExerciseSetItem(
                        entry = entry,
                        exerciseCategory = exerciseCategory,
                        revisedSetText = revisedSetsTexts[sortedEntries.indexOf(entry)],
                    )
                }
            }
        }
    }
}

@Composable
private fun SessionExerciseSetItem(
    entry: ExerciseLogEntry,
    exerciseCategory: ExerciseCategory?,
    revisedSetText: Pair<String, Color?>,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(LiftingTheme.colors.background),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = revisedSetText.first,
                    style = LiftingTheme.typography.caption,
                    color = revisedSetText.second ?: LiftingTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.xLarge)
            ) {
                exerciseCategory?.fields?.forEach { field ->
                    SetColumnItem(
                        entry = entry,
                        fieldType = field
                    )
                }
            }
        }

        if (entry.personalRecords.isNullOrEmpty().not()) {
            PersonalRecordsRowComponent(
                modifier = Modifier
                    .padding(top = 8.dp, start = 32.dp, end = 16.dp),
                prs = entry.personalRecords!!
            )
        }
    }
}

@Composable
private fun SetColumnItem(
    entry: ExerciseLogEntry,
    fieldType: SetFieldValueType,
) {
    when (fieldType) {
        SetFieldValueType.WEIGHT,
        SetFieldValueType.ADDITIONAL_WEIGHT,
        SetFieldValueType.ASSISTED_WEIGHT -> SetColumnItem(
            value = entry.weight.toWeightUnitPreferencesString(),
            title = preferencesWeightUnitString()
        )

        SetFieldValueType.REPS -> SetColumnItem(
            value = (entry.reps ?: 0).toString(),
            title = stringResource(id = R.string.reps_lowercase)
        )

        SetFieldValueType.DISTANCE -> SetColumnItem(
            value = entry.distance.toDistanceUnitPreferencesString(),
            title = preferencesDistanceUnitString()
        )

        SetFieldValueType.DURATION -> SetColumnItem(
            value = entry.timeRecorded?.toMMSSFromString() ?: "",
            title = stringResource(id = R.string.duration2)
        )

        SetFieldValueType.RPE -> if ((entry.rpe ?: 0f) > 0f) {
            SetColumnItem(
                value = entry.rpe?.toRpe().toString(),
                title = stringResource(id = R.string.rpe)
            )
        }
    }
}

@Composable
private fun SetColumnItem(value: String, title: String) {
    Text(text = buildAnnotatedString {
        withStyle(style = SpanStyle(LiftingTheme.colors.onBackground)) {
            append(value)
        }
        withStyle(style = SpanStyle(LiftingTheme.colors.onBackground.copy(alpha = 0.65f))) {
            append(" $title")
        }
    })
}

private val ExerciseLogEntryComparator = Comparator<ExerciseLogEntry> { left, right ->
    left.setNumber?.compareTo(right.setNumber ?: 0) ?: 0
}