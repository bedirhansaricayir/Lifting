package com.lifting.app.feature.workout_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.feature.workout_edit.components.LiftingWorkoutEditor
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
internal fun WorkoutEditScreenContent(
    state: WorkoutEditUIState,
    onEvent: (WorkoutEditUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        WorkoutEditUIState.Loading -> {}
        WorkoutEditUIState.Error -> {}
        is WorkoutEditUIState.Success ->
            WorkoutEditScreenSuccess(
                state = state,
                onEvent = onEvent,
                modifier = modifier,
            )

    }
}

@Composable
internal fun WorkoutEditScreenSuccess(
    state: WorkoutEditUIState.Success,
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
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.back,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
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
                barbells = emptyList(),
                onAddExerciseButtonClicked = {
                    onEvent(WorkoutEditUIEvent.OnAddExerciseButtonClicked)
                },
                onChangeWorkoutName = {
                    onEvent(WorkoutEditUIEvent.OnWorkoutNameChanged(it))
                },
                onChangeWorkoutNote = {
                    onEvent(WorkoutEditUIEvent.OnWorkoutNoteChanged(it))
                },
                onCancelCurrentWorkout = { },
                onDeleteExerciseFromWorkout = {
                    onEvent(WorkoutEditUIEvent.OnDeleteExerciseClicked(it))
                },
                onAddEmptySetToExercise = { setNumber, exerciseWorkoutJunction ->
                    onEvent(WorkoutEditUIEvent.OnAddSetClicked(setNumber, exerciseWorkoutJunction))
                },
                onDeleteLogEntry = {
                    onEvent(WorkoutEditUIEvent.OnLogEntryDeleted(it))
                },
                onUpdateLogEntry = {
                    onEvent(WorkoutEditUIEvent.OnLogEntryUpdated(it))
                },
                onUpdateWarmUpSets = { logEntriesWithExercise, exerciseLogEntries ->
                    onEvent(
                        WorkoutEditUIEvent.OnWarmUpSetsUpdated(
                            logEntriesWithExercise,
                            exerciseLogEntries
                        )
                    )
                },
                onAddEmptyNote = {
                    onEvent(WorkoutEditUIEvent.OnAddNoteClicked(it))
                },
                onDeleteNote = {
                    onEvent(WorkoutEditUIEvent.OnDeleteNoteClicked(it))
                },
                onChangeNote = {
                    onEvent(WorkoutEditUIEvent.OnNoteChanged(it))
                },
                onAddToSuperset = { junctionId, supersetId ->

                },
                onRemoveFromSuperset = {

                },
                onUpdateBarbell = { junctionId, barbellId ->

                }
            )
        }
    )
}