package com.lifting.app.feature.workout_editor

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.LogSetType
import com.lifting.app.core.model.WarmUpSet
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.common.LocalAppSettings
import com.lifting.app.core.ui.common.PreventKeyboardOnResume
import com.lifting.app.core.ui.common.toWeightUnit
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonDefaults
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.LiftingSwipeToDismissBox
import com.lifting.app.core.ui.extensions.darkerOrLighter
import com.lifting.app.core.ui.extensions.randomColorById
import com.lifting.app.feature.workout_editor.utils.WorkoutEditorUtils
import com.lifting.app.feature.workout_editor.warmup_calculator.WarmUpCalculatorDialog

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

private val ExerciseLogEntryComparator = Comparator<ExerciseLogEntry> { left, right ->
    left.setNumber?.compareTo(right.setNumber ?: 0) ?: 0
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.workoutExerciseItemComponent(
    logEntriesWithJunction: LogEntriesWithExercise,
    barbells: List<Barbell>,
    onUpdateWarmUpSets: (List<WarmUpSet>) -> Unit,
    onValuesUpdated: (updatedEntry: ExerciseLogEntry) -> Unit,
    onSwipeDelete: (ExerciseLogEntry) -> Unit,
    onAddSet: () -> Unit,
    onDeleteExercise: () -> Unit,
    onAddEmptyNote: () -> Unit,
    onDeleteNote: (ExerciseSetGroupNote) -> Unit,
    onChangeNote: (ExerciseSetGroupNote) -> Unit,
    onAddToSuperset: () -> Unit,
    onRemoveFromSuperset: () -> Unit,
    onUpdateBarbellClicked: () -> Unit,
) {
    val supersetId = logEntriesWithJunction.junction.supersetId
    val exercise = logEntriesWithJunction.exercise
    val logEntries = logEntriesWithJunction.logEntries
    val notes = logEntriesWithJunction.notes ?: emptyList()
    val sortedEntries = logEntries.sortedWith(ExerciseLogEntryComparator)
    val barbell = barbells.find { b -> b.id == logEntriesWithJunction.junction.barbellId }

    item(key = "${logEntriesWithJunction.junction.id}_exercise_info") {

        var isExpandedPopupActions by remember { mutableStateOf(false) }
        var warmUpSetsDialogVisible by rememberSaveable { mutableStateOf(false) }
        var dialogWarmUpSets by remember { mutableStateOf<List<WarmUpSet>>(emptyList()) }
        var warmUpWorkSetWeight by rememberSaveable { mutableStateOf<Double?>(0.0) }

        fun updateWarmUpSets(newWarmUpWorkSetWeight: Double, newWarmUpSets: List<WarmUpSet>) {
            dialogWarmUpSets = newWarmUpSets
            warmUpWorkSetWeight = newWarmUpWorkSetWeight
            onUpdateWarmUpSets(newWarmUpSets)
        }

        LaunchedEffect(key1 = barbell) {
            if (dialogWarmUpSets.any { it.findFormula().contains("Bar") }) {
                dialogWarmUpSets = dialogWarmUpSets.map {
                    if (it.findFormula().contains("Bar")) {
                        it.copy(weight = barbell.toWeightUnit(WeightUnit.Kg))
                    } else {
                        it
                    }
                }
            }
        }

        LaunchedEffect(key1 = sortedEntries) {
            val newWorkWeight = (sortedEntries.filter { it.setType != LogSetType.WARM_UP }
                .getOrNull(0)?.weight ?: warmUpWorkSetWeight)

            val lastDialogWarmupSets = dialogWarmUpSets.toList()

            if (warmUpWorkSetWeight != newWorkWeight) {
                warmUpWorkSetWeight = newWorkWeight
                dialogWarmUpSets =
                    WarmUpSet.refreshWarmupSetsWithNewWorkWeight(
                        newWorkWeight,
                        lastDialogWarmupSets
                    )
            }
        }

        if (warmUpSetsDialogVisible) {
            key(LocalAppSettings.current.weightUnit) {
                WarmUpCalculatorDialog(
                    startingWorkSetWeight = warmUpWorkSetWeight,
                    barbell = barbell,
                    startingSets = dialogWarmUpSets,
                    onInsert = { newWarmUpWorkSetWeight, newWarmUpSets ->
                        updateWarmUpSets(newWarmUpWorkSetWeight, newWarmUpSets)
                    },
                    onDismissRequest = { warmUpSetsDialogVisible = false }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = LiftingTheme.dimensions.large,
                    end = LiftingTheme.dimensions.small,
                )
                .animateItem(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val supersetColor by remember(supersetId) {
                mutableStateOf(if (supersetId != null) Color.randomColorById(supersetId) else null)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large)
            ) {
                Text(
                    text = exercise.name.toString(),
                    style = LiftingTheme.typography.header2,
                    color = LiftingTheme.colors.primary,
                )

                supersetColor?.let { color ->
                    Box(
                        modifier = Modifier
                            .background(color.copy(.5f), CircleShape)
                            .padding(LiftingTheme.dimensions.small),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.superset),
                            style = LiftingTheme.typography.button,
                            color = color.darkerOrLighter(0.8f)
                        )
                    }
                }
            }


            Column {
                LiftingButton(
                    buttonType = LiftingButtonType.IconButton(
                        icon = LiftingTheme.icons.moreVert,
                        tint = LiftingTheme.colors.primary,
                    ),
                    onClick = { isExpandedPopupActions = true }
                )

                ExercisePopupActions(
                    isExpanded = isExpandedPopupActions,
                    onDismissRequest = { isExpandedPopupActions = false },
                    onDeleteExercise = onDeleteExercise,
                    onAddNote = onAddEmptyNote,
                    onClickBarbell = onUpdateBarbellClicked,
                    onAddWarmUpSets = {
                        warmUpSetsDialogVisible = true
                    },
                    onAddToSuperset = onAddToSuperset,
                    onRemoveFromSuperset = onRemoveFromSuperset,
                    isInSuperset = logEntriesWithJunction.junction.supersetId != null,
                )

            }
        }

    }

    items(items = notes, key = { it.id }) {
        NoteField(
            modifier = Modifier.animateItem(),
            note = it,
            onDeleteNote = { onDeleteNote(it) },
            onChangeValue = { newValue ->
                onChangeNote(it.copy(note = newValue))
            }
        )
    }

    item(key = "${logEntriesWithJunction.junction.id}_titles") {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LiftingTheme.colors.background)
                .padding(horizontal = LiftingTheme.dimensions.small)
                .animateItem(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SetFieldTitleColumns(exercise = exercise)

            Box(
                modifier = Modifier.weight(0.5f),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = LiftingTheme.icons.done,
                    contentDescription = null,
                    tint = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
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

    val revisedSetsTexts = getRevisedSetNumbers()

    items(items = sortedEntries, key = { it.entryId }) { entry ->
        val appSettings = LocalAppSettings.current
        key(barbell, appSettings.weightUnit, appSettings.distanceUnit) {
            SetItem(
                revisedSetText = revisedSetsTexts[sortedEntries.indexOf(entry)],
                exercise = exercise,
                exerciseLogEntry = entry,
                barbell = barbell,
                onChange = {
                    onValuesUpdated(it)
                },
                onSwipeDelete = {
                    onSwipeDelete(it)
                },
            )
        }
    }

    item(key = "${logEntriesWithJunction.junction.id}_add_set_button") {
        LiftingButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = LiftingTheme.dimensions.large,
                    end = LiftingTheme.dimensions.large,
                    top = LiftingTheme.dimensions.small
                )
                .animateItem(),
            buttonType = LiftingButtonType.PrimaryButton(
                text = stringResource(R.string.add_set),
                leadingIcon = LiftingTheme.icons.add
            ),
            colors = LiftingButtonDefaults.primaryButtonColors(
                containerColor = LiftingTheme.colors.surface,
                contentColor = LiftingTheme.colors.onBackground,
            ),
            onClick = onAddSet
        )
    }

    item(key = "${logEntriesWithJunction.junction.id}_bottom_space") {
        Spacer(modifier = Modifier.height(LiftingTheme.dimensions.large))
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun LazyItemScope.SetItem(
    exercise: Exercise,
    barbell: Barbell?,
    revisedSetText: Pair<String, Color?>,
    exerciseLogEntry: ExerciseLogEntry,
    onChange: (ExerciseLogEntry) -> Unit,
    onSwipeDelete: (ExerciseLogEntry) -> Unit,
) {
    PreventKeyboardOnResume()

    var mLogEntry by rememberSaveable {
        mutableStateOf(exerciseLogEntry)
    }

    val completionAnimDuration = 200
    val completionAnimSpecFloat =
        tween<Float>(
            durationMillis = completionAnimDuration,
            easing = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)
        )
    val completionAnimSpecColor =
        tween<Color>(
            durationMillis = completionAnimDuration,
            easing = LinearEasing//CubicBezierEasing(0.5f, 1f, 0.89f, 1f)
        )

    val containerColor by animateColorAsState(
        targetValue = if (mLogEntry.completed) LiftingTheme.colors.primary else LiftingTheme.colors.background,
        animationSpec = completionAnimSpecColor
    )

    var isScaleAnimRunning by rememberSaveable {
        mutableStateOf(false)
    }

    val scale by animateFloatAsState(
        targetValue = if (isScaleAnimRunning) 1.05f else 1f,
        animationSpec = completionAnimSpecFloat,
        finishedListener = {
            isScaleAnimRunning = false
        }
    )

    fun handleOnChange(updatedEntry: ExerciseLogEntry) {
        if (updatedEntry.completed) {
            isScaleAnimRunning = false
        }
        //updatedEntry.completed = false
        val updated = updatedEntry.copy(completed = false)

        mLogEntry = updatedEntry
        onChange(updated)
    }

    fun handleOnCompleteChange(isComplete: Boolean) {
        val isCompletable = WorkoutEditorUtils.isValidSet(
            logEntry = exerciseLogEntry,
            exerciseCategory = exercise.category
        )

        if (isComplete && isCompletable) {
            isScaleAnimRunning = true
        }

        val updatedEntry =
            mLogEntry.copy(
                completed = if (isComplete && isCompletable) {
                    true
                } else if (!isComplete) {
                    isComplete
                } else {
                    false
                }
            )
        mLogEntry = updatedEntry
        onChange(updatedEntry)
    }

    LiftingSwipeToDismissBox(
        modifier = Modifier
            .scale(scale)
            .animateItem(),
        containerColor = containerColor,
        onSwipeDelete = {
            onSwipeDelete(exerciseLogEntry)
        }
    ) {
        SetItemLayout(
            containerColor = containerColor,
            exercise = exercise,
            exerciseLogEntry = mLogEntry,
            revisedSetText = revisedSetText,
            barbell = barbell,
            onWeightChange = { _, value ->
                handleOnChange(mLogEntry.copy(weight = value))
            },
            onRepsChange = { _, value ->
                handleOnChange(mLogEntry.copy(reps = value))
            },
            onDistanceChange = { _, value ->
                handleOnChange(mLogEntry.copy(distance = value))
            },
            onDurationChange = { _, value ->
                handleOnChange(mLogEntry.copy(timeRecorded = value))
            },
            onCompleteChange = { _, value ->
                handleOnCompleteChange(value)
            },
            onSetTypeChange = { _, value ->
                handleOnChange(mLogEntry.copy(setType = value))
            },
            onRpeChange = { _, value ->
                handleOnChange(mLogEntry.copy(rpe = value))
            }
        )
    }
}

@Composable
private fun SetItemLayout(
    containerColor: Color,
    exercise: Exercise,
    exerciseLogEntry: ExerciseLogEntry,
    revisedSetText: Pair<String, Color?>,
    barbell: Barbell?,
    onWeightChange: (ExerciseLogEntry, Double?) -> Unit,
    onRepsChange: (ExerciseLogEntry, Int?) -> Unit,
    onDistanceChange: (ExerciseLogEntry, Double?) -> Unit,
    onDurationChange: (ExerciseLogEntry, Long?) -> Unit,
    onCompleteChange: (ExerciseLogEntry, Boolean) -> Unit,
    onSetTypeChange: (ExerciseLogEntry, LogSetType) -> Unit,
    onRpeChange: (ExerciseLogEntry, Float?) -> Unit
) {
    val typeOfSet = exerciseLogEntry.setType ?: LogSetType.NORMAL
    var isSetTypeChangerExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = containerColor)
            .padding(
                horizontal = LiftingTheme.dimensions.small,
                vertical = LiftingTheme.dimensions.extraSmall
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .padding(horizontal = 8.dp)
                .weight(0.5f)
                .clip(LiftingTheme.shapes.large)
                .background(
                    color = LiftingTheme.colors.surface,
                    shape = LiftingTheme.shapes.large
                )
                .clickable {
                    isSetTypeChangerExpanded = true
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = revisedSetText.first,
                style = LiftingTheme.typography.caption,
                color = revisedSetText.second ?: LiftingTheme.colors.onBackground,
                textAlign = TextAlign.Center,
            )
            SetTypeChangerMenu(
                selectedType = typeOfSet,
                expanded = isSetTypeChangerExpanded,
                onDismissRequest = {
                    isSetTypeChangerExpanded = false
                },
                onChangeSetType = {
                    isSetTypeChangerExpanded = false
                    onSetTypeChange(exerciseLogEntry, it)
                }
            )
        }


        SetFieldColumns(
            exercise = exercise,
            barbell = barbell,
            exerciseLogEntry = exerciseLogEntry,
            onWeightChange = onWeightChange,
            onDistanceChange = onDistanceChange,
            onRepsChange = onRepsChange,
            onDurationChange = onDurationChange,
            onRpeChange = onRpeChange,
        )

        IconButton(
            onClick = {
                onCompleteChange(
                    exerciseLogEntry,
                    !exerciseLogEntry.completed
                )
            },
            modifier = Modifier.weight(0.5f)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        shape = CircleShape,
                        color = LiftingTheme.colors.surface
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = LiftingTheme.icons.outlinedDone,
                    contentDescription = String.Companion.EMPTY,
                    tint = LiftingTheme.colors.onBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}