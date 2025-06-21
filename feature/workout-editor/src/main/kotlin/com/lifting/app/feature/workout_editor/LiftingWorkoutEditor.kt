package com.lifting.app.feature.workout_editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lifting.app.core.common.extensions.clearRouteArgument
import com.lifting.app.core.common.extensions.getBackStackArgs
import com.lifting.app.core.common.extensions.observeRouteArgument
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.ExerciseLogEntry
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.model.ExerciseWorkoutJunc
import com.lifting.app.core.model.LogEntriesWithExercise
import com.lifting.app.core.model.toExerciseLogEntries
import com.lifting.app.core.navigation.LocalNavHostController
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.RESULT_EXERCISES_SCREEN_EXERCISE_ID
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_BARBELL_JUNCTION_RESULT
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonDefaults
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.LiftingTextField

/**
 * Created by bedirhansaricayir on 08.02.2025
 */
@Composable
fun LiftingWorkoutEditor(
    navController: NavController = LocalNavHostController.current,
    workoutName: String?,
    workoutNote: String?,
    ongoingWorkout: Boolean,
    logEntriesWithJunction: List<LogEntriesWithExercise>,
    barbells: List<Barbell> = emptyList(),
    onExerciseAdded: (String) -> Unit,
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
    onAddToSupersetClicked: () -> Unit,
    onRemoveFromSuperset: (LogEntriesWithExercise) -> Unit,
    onSupersetUpdated: (String) -> Unit,
    onUpdateBarbellClicked: () -> Unit,
    onBarbellUpdated: (barbellWithJunctionId: String) -> Unit,
    addNavigationBarPadding: Boolean = false,
    layoutAtTop: @Composable LazyItemScope.() -> Unit = {},
) {
    val density = LocalDensity.current
    val navigationBarHeight = with(density) { if (addNavigationBarPadding) WindowInsets.navigationBars.getBottom(density).dp else 0.dp }

    val exerciseId = navController.observeRouteArgument(RESULT_EXERCISES_SCREEN_EXERCISE_ID)
        ?.collectAsStateWithLifecycle()

    val barbellWithJunctionId = navController.observeRouteArgument(SELECTED_BARBELL_JUNCTION_RESULT)
        ?.collectAsStateWithLifecycle()

    val superSetResult = navController.observeRouteArgument(RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY)
        ?.collectAsStateWithLifecycle()

    LaunchedEffect(exerciseId?.value) {
        exerciseId?.value?.let {
            onExerciseAdded(it)
        }.also {
            navController.clearRouteArgument(RESULT_EXERCISES_SCREEN_EXERCISE_ID)
        }
    }

    LaunchedEffect(barbellWithJunctionId?.value) {
        barbellWithJunctionId?.value?.let {
            onBarbellUpdated(it)
        }.also {
            navController.clearRouteArgument(SELECTED_BARBELL_JUNCTION_RESULT)
        }
    }

    LaunchedEffect(superSetResult?.value) {
        superSetResult?.value?.let {
            if (it.getBackStackArgs().size > 2) {
                onSupersetUpdated(it)
            }
        }.also {
            navController.clearRouteArgument(RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY)
        }
    }


    fun handleRemoveFromSuperset(item: LogEntriesWithExercise) {
        onRemoveFromSuperset(item)

        val itemsWithSupersetId = logEntriesWithJunction.filter {
            it.junction.supersetId == item.junction.supersetId && it.junction.id != item.junction.id
        }

        if (itemsWithSupersetId.size == 1) {
            onRemoveFromSuperset(itemsWithSupersetId[0])
        }
    }

    fun handleDeleteExercise(item: LogEntriesWithExercise) {
        handleRemoveFromSuperset(item)
        onDeleteExerciseFromWorkout(item)
    }

    fun handleSwipeDelete(item: LogEntriesWithExercise, entryToDelete: ExerciseLogEntry) {
        if (item.logEntries.size == 1) {
            onDeleteExerciseFromWorkout(item)
            handleRemoveFromSuperset(item)

        } else {
            onDeleteLogEntry(entryToDelete)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background),
        contentPadding = PaddingValues(
            bottom = 250.dp + navigationBarHeight
        )
    ) {
        item(key = "workout_panel_top_quick_info") {
            layoutAtTop()
        }
        item(key = "text_fields") {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateItem(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LiftingTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = workoutName.orEmpty(),
                    onValueChange = { onChangeWorkoutName(it) },
                    placeholder = stringResource(id = R.string.workout_name)
                )
                LiftingTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = workoutNote.orEmpty(),
                    onValueChange = { onChangeWorkoutNote(it) },
                    placeholder = stringResource(id = R.string.workout_note)
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
                    handleSwipeDelete(logEntriesWithJunctionItem, entryToDelete)
                },
                onAddSet = {
                    val setNumber = runCatching {
                        logEntriesWithJunctionItem.logEntries[logEntriesWithJunctionItem.logEntries.size - 1].setNumber!! + 1
                    }.getOrElse { 1 }

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
                    handleRemoveFromSuperset(logEntriesWithJunctionItem)
                },
                onAddToSuperset = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY,
                        value = "${logEntriesWithJunctionItem.junction.workoutId!!},${logEntriesWithJunctionItem.junction.id}"
                    )
                    onAddToSupersetClicked()

                },
                onUpdateBarbellClicked = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        SELECTED_BARBELL_JUNCTION_RESULT,
                        value = "${logEntriesWithJunctionItem.junction.id},${logEntriesWithJunctionItem.junction.barbellId}"
                    )
                    onUpdateBarbellClicked()
                }
            )
        }

        item(key = "add_exercise_button") {
            LiftingButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .animateItem(),
                buttonType = LiftingButtonType.PrimaryButton(
                    text = stringResource(id = R.string.add_exercise),
                    leadingIcon = LiftingTheme.icons.add
                ),
                onClick = onAddExerciseButtonClicked

            )
        }

        if (ongoingWorkout) {
            item(key = "cancel_workout_button") {
                LiftingButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .animateItem(),
                    buttonType = LiftingButtonType.TextButton(text = stringResource(id = R.string.cancel_workout)),
                    colors = LiftingButtonDefaults.textButtonColors(contentColor = LiftingTheme.colors.error),
                    onClick = onCancelCurrentWorkout
                )
            }
        }
    }
}