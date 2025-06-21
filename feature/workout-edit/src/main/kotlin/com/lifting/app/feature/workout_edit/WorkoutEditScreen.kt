package com.lifting.app.feature.workout_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.workout_editor.LiftingWorkoutEditor
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 08.02.2025
 */

@Composable
internal fun WorkoutEditScreen(
    state: WorkoutEditUIState,
    onEvent: (WorkoutEditUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
     WorkoutEditScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier,
    )
}

@Composable
private fun WorkoutEditScreenContent(
    state: WorkoutEditUIState,
    onEvent: (WorkoutEditUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolBarScaffold(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background),
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = if (state.isTemplate) R.string.edit_template else R.string.edit_workout),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                navigationIcon = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.back,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutEditUIEvent.OnBackClicked) }
                    )
                }
            )
        },
        body = {
            LiftingWorkoutEditor(
                workoutName = state.workout?.name,
                workoutNote = state.workout?.note,
                ongoingWorkout = false,
                logEntriesWithJunction = state.logEntriesWithExercise,
                barbells = state.barbells,
                onAddExerciseButtonClicked = {
                    onEvent(WorkoutEditUIEvent.OnAddExerciseButtonClicked)
                },
                onExerciseAdded = { exerciseId ->
                    onEvent(WorkoutEditUIEvent.OnExerciseAdded(exerciseId))
                },
                onChangeWorkoutName = { workoutName ->
                    onEvent(WorkoutEditUIEvent.OnWorkoutNameChanged(workoutName))
                },
                onChangeWorkoutNote = { workoutNote ->
                    onEvent(WorkoutEditUIEvent.OnWorkoutNoteChanged(workoutNote))
                },
                onCancelCurrentWorkout = { },
                onDeleteExerciseFromWorkout = { logEntriesWithExercise ->
                    onEvent(WorkoutEditUIEvent.OnDeleteExerciseClicked(logEntriesWithExercise))
                },
                onAddEmptySetToExercise = { setNumber, exerciseWorkoutJunction ->
                    onEvent(WorkoutEditUIEvent.OnAddSetClicked(setNumber, exerciseWorkoutJunction))
                },
                onDeleteLogEntry = { exerciseLogEntry ->
                    onEvent(WorkoutEditUIEvent.OnLogEntryDeleted(exerciseLogEntry))
                },
                onUpdateLogEntry = { exerciseLogEntry ->
                    onEvent(WorkoutEditUIEvent.OnLogEntryUpdated(exerciseLogEntry))
                },
                onUpdateWarmUpSets = { logEntriesWithExercise, exerciseLogEntries ->
                    onEvent(
                        WorkoutEditUIEvent.OnWarmUpSetsUpdated(
                            logEntriesWithExercise,
                            exerciseLogEntries
                        )
                    )
                },
                onAddEmptyNote = { logEntriesWithExercise ->
                    onEvent(WorkoutEditUIEvent.OnAddNoteClicked(logEntriesWithExercise))
                },
                onDeleteNote = { exerciseGroupNote ->
                    onEvent(WorkoutEditUIEvent.OnDeleteNoteClicked(exerciseGroupNote))
                },
                onChangeNote = { exerciseGroupNote ->
                    onEvent(WorkoutEditUIEvent.OnNoteChanged(exerciseGroupNote))
                },
                onAddToSupersetClicked = {
                    onEvent(WorkoutEditUIEvent.OnAddToSupersetClicked)
                },
                onRemoveFromSuperset = { logEntriesWithExercise ->
                    onEvent(WorkoutEditUIEvent.OnRemoveFromSupersetClicked(logEntriesWithExercise))
                },
                onSupersetUpdated = { supersetResult ->
                    onEvent(WorkoutEditUIEvent.OnSupersetUpdated(supersetResult))
                },
                onUpdateBarbellClicked = {
                    onEvent(WorkoutEditUIEvent.OnUpdateBarbellClicked)
                },
                onBarbellUpdated = { barbellWithJunctionId ->
                    onEvent(WorkoutEditUIEvent.OnBarbellUpdated(barbellWithJunctionId))
                }
            )
        }
    )
}