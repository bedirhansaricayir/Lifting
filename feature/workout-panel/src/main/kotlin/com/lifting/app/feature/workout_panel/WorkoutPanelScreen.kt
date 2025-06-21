package com.lifting.app.feature.workout_panel

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.feature.workout_editor.LiftingWorkoutEditor
import com.lifting.app.feature.workout_panel.components.WorkoutQuickInfo

/**
 * Created by bedirhansaricayir on 07.05.2025
 */
@Composable
internal fun WorkoutPanelScreen(
    state: WorkoutPanelUIState,
    onEvent: (WorkoutPanelUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    WorkoutPanelScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
internal fun WorkoutPanelScreenContent(
    state: WorkoutPanelUIState,
    onEvent: (WorkoutPanelUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        WorkoutPanelUIState.Loading -> {}
        WorkoutPanelUIState.Error -> {}
        is WorkoutPanelUIState.Success -> WorkoutPanelScreenSuccess(state, onEvent, modifier)
    }
}

@Composable
internal fun WorkoutPanelScreenSuccess(
    state: WorkoutPanelUIState.Success,
    onEvent: (WorkoutPanelUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LiftingWorkoutEditor(
        workoutName = state.workout.name,
        workoutNote = state.workout.note,
        ongoingWorkout = true,
        addNavigationBarPadding = true,
        logEntriesWithJunction = state.logs,
        barbells = state.barbells,
        layoutAtTop = {
            Column(
                modifier = Modifier.animateItem()
            ) {
                WorkoutQuickInfo(
                    currentDuration = state.currentDuration,
                    currentVolume = state.currentVolume,
                    currentSets = state.currentSets
                )
                HorizontalDivider(
                    color = LiftingTheme.colors.onBackground.copy(0.5f),
                )
            }
        },
        onAddExerciseButtonClicked = {
            onEvent(WorkoutPanelUIEvent.OnAddExerciseButtonClicked)
        },
        onExerciseAdded = { exerciseId ->
            onEvent(WorkoutPanelUIEvent.OnExerciseAdded(exerciseId))
        },
        onChangeWorkoutName = { workoutName ->
            onEvent(WorkoutPanelUIEvent.OnWorkoutNameChanged(workoutName))
        },
        onChangeWorkoutNote = { workoutNote ->
            onEvent(WorkoutPanelUIEvent.OnWorkoutNoteChanged(workoutNote))
        },
        onCancelCurrentWorkout = {
            onEvent(WorkoutPanelUIEvent.OnCancelWorkoutClicked)
        },
        onDeleteExerciseFromWorkout = { logEntriesWithExercise ->
            onEvent(WorkoutPanelUIEvent.OnDeleteExerciseClicked(logEntriesWithExercise))
        },
        onAddEmptySetToExercise = { setNumber, exerciseWorkoutJunction ->
            onEvent(WorkoutPanelUIEvent.OnAddSetClicked(setNumber, exerciseWorkoutJunction))
        },
        onDeleteLogEntry = { exerciseLogEntry ->
            onEvent(WorkoutPanelUIEvent.OnLogEntryDeleted(exerciseLogEntry))
        },
        onUpdateLogEntry = { exerciseLogEntry ->
            onEvent(WorkoutPanelUIEvent.OnLogEntryUpdated(exerciseLogEntry))
        },
        onUpdateWarmUpSets = { logEntriesWithExercise, exerciseLogEntries ->
            onEvent(
                WorkoutPanelUIEvent.OnWarmUpSetsUpdated(
                    logEntriesWithExercise,
                    exerciseLogEntries
                )
            )
        },
        onAddEmptyNote = { logEntriesWithExercise ->
            onEvent(WorkoutPanelUIEvent.OnAddNoteClicked(logEntriesWithExercise))
        },
        onDeleteNote = { exerciseGroupNote ->
            onEvent(WorkoutPanelUIEvent.OnDeleteNoteClicked(exerciseGroupNote))
        },
        onChangeNote = { exerciseGroupNote ->
            onEvent(WorkoutPanelUIEvent.OnNoteChanged(exerciseGroupNote))
        },
        onAddToSupersetClicked = {
            onEvent(WorkoutPanelUIEvent.OnAddToSupersetClicked)
        },
        onRemoveFromSuperset = { logEntriesWithExercise ->
            onEvent(WorkoutPanelUIEvent.OnRemoveFromSupersetClicked(logEntriesWithExercise))
        },
        onSupersetUpdated = { supersetResult ->
            onEvent(WorkoutPanelUIEvent.OnSupersetUpdated(supersetResult))
        },
        onUpdateBarbellClicked = {
            onEvent(WorkoutPanelUIEvent.OnUpdateBarbellClicked)
        },
        onBarbellUpdated = { barbellWithJunctionId ->
            onEvent(WorkoutPanelUIEvent.OnBarbellUpdated(barbellWithJunctionId))
        }
    )
}