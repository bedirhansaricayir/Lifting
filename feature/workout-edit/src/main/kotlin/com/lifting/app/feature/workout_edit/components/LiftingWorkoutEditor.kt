package com.lifting.app.feature.workout_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.toExerciseLogEntries
import com.lifting.app.core.ui.components.LiftingTextButton
import com.lifting.app.core.ui.components.LiftingTextField

/**
 * Created by bedirhansaricayir on 08.02.2025
 */
@Composable
fun LiftingWorkoutEditor(
    useReboundKeyboard: Boolean = false,
    workoutName: String?,
    workoutNote: String?,
    ongoingWorkout: Boolean,
    logEntriesWithJunction: List<LogEntriesWithExercise>,
    barbells: List<Barbell>,
    onAddExerciseButtonClicked: () -> Unit,
    onChangeWorkoutName: (String) -> Unit,
    onChangeWorkoutNote: (String) -> Unit,
    onCancelCurrentWorkout: () -> Unit,
    onDeleteExerciseFromWorkout: (LogEntriesWithExercise) -> Unit,
    onAddEmptySetToExercise: (setNumber: Int, exerciseWorkoutJunction: ExerciseWorkoutJunc) -> Unit,
    onDeleteLogEntry: (ExerciseLogEntry) -> Unit,
    onUpdateLogEntry: (ExerciseLogEntry) -> Unit,
    onUpdateWarmUpSets: (LogEntriesWithExercise, List<ExerciseLogEntry>) -> Unit,
    onAddEmptyNote: (LogEntriesWithExercise) -> Unit,
    onDeleteNote: (ExerciseSetGroupNote) -> Unit,
    onChangeNote: (ExerciseSetGroupNote) -> Unit,
    onAddToSuperset: (junctionId: String, supersetId: Int) -> Unit,
    onRemoveFromSuperset: (LogEntriesWithExercise) -> Unit,
    onUpdateBarbell: (junctionId: String, barbellId: String) -> Unit,
) {
    val handleRemoveFromSuperSet: (LogEntriesWithExercise) -> Unit = {
        onRemoveFromSuperset(it)

        val itemsWithSupersetId = logEntriesWithJunction.filter {
            it.junction.supersetId == it.junction.supersetId && it.junction.id != it.junction.id
        }

        if (itemsWithSupersetId.size == 1) {
            onRemoveFromSuperset(itemsWithSupersetId[0])
        }
    }

    val handleDeleteExercise: (LogEntriesWithExercise) -> Unit = {
        handleRemoveFromSuperSet(it)
        onDeleteExerciseFromWorkout(it)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background),
    ) {
        item(key = "text_fields") {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateItemPlacement(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LiftingTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = workoutName.orEmpty(),
                    onValueChange = { onChangeWorkoutName(it) },
                    placeholder = stringResource(id = com.lifting.app.core.ui.R.string.workout_name)
                )
                LiftingTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = workoutNote.orEmpty(),
                    onValueChange = { onChangeWorkoutNote(it) },
                    placeholder = stringResource(id = com.lifting.app.core.ui.R.string.workout_note)
                )
            }
        }

        for (logEntriesWithJunctionItem in logEntriesWithJunction) {
            workoutExerciseItemComponent(
                logEntriesWithJunction = logEntriesWithJunctionItem,
                barbells = barbells,
                onValuesUpdated = { updatedEntry ->
                    onUpdateLogEntry(updatedEntry)
                },
                onSwipeDelete = { entryToDelete ->
                    if (logEntriesWithJunctionItem.logEntries.size == 1) {
                        onDeleteExerciseFromWorkout(logEntriesWithJunctionItem)
                    } else {
                        onDeleteLogEntry(entryToDelete)
                    }
                },
                onAddSet = {
                    val setNumber = try {
                        logEntriesWithJunctionItem.logEntries[logEntriesWithJunctionItem.logEntries.size - 1].setNumber!! + 1
                    } catch (e: Exception) {
                        1
                    }

                    onAddEmptySetToExercise(
                        setNumber,
                        logEntriesWithJunctionItem.junction
                    )
                },
                onDeleteExercise = {
                    handleDeleteExercise(logEntriesWithJunctionItem)
                },
                onUpdateWarmUpSets = {
                    onUpdateWarmUpSets(logEntriesWithJunctionItem, it.toExerciseLogEntries())
                },
                onAddEmptyNote = {
                    onAddEmptyNote(logEntriesWithJunctionItem)
                },
                onChangeNote = onChangeNote,
                onDeleteNote = onDeleteNote,
                onRemoveFromSuperset = {
                    handleRemoveFromSuperSet(logEntriesWithJunctionItem)
                },
                onAddToSuperset = {

                },
                onRequestBarbellChanger = {

                }
            )
        }

        item(key = "add_exercise_button") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .animateItem(),
                onClick = {
                    onAddExerciseButtonClicked()
                }
            ) {
                Icon(
                    imageVector = LiftingTheme.icons.add,
                    contentDescription = "Add Exercise Icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = LiftingTheme.colors.onPrimary
                )
                Text(
                    text = stringResource(com.lifting.app.core.ui.R.string.add_exercise),
                    style = LiftingTheme.typography.button
                )
            }
        }

        if (ongoingWorkout) {
            item(key = "cancel_workout_button") {
                LiftingTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .animateItem(),
                    colors = ButtonDefaults.textButtonColors(contentColor = LiftingTheme.colors.error),
                    text = stringResource(id = com.lifting.app.core.ui.R.string.cancel_workout),
                    onClick = {
                        onCancelCurrentWorkout()
                    }
                )
            }
        }
    }
}
