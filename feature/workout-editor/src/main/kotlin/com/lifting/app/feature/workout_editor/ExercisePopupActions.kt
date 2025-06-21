package com.lifting.app.feature.workout_editor

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.common.UiText


/**
 * Created by bedirhansaricayir on 23.02.2025
 */

@Composable
internal fun ExercisePopupActions(
    isExpanded: Boolean,
    isInSuperset: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteExercise: () -> Unit,
    onAddWarmUpSets: () -> Unit,
    onAddNote: () -> Unit,
    onClickBarbell: () -> Unit,
    onAddToSuperset: () -> Unit,
    onRemoveFromSuperset: () -> Unit,
) {
    val itemList = listOf(
        ExercisePopupAction.AddNote,
        ExercisePopupAction.WarmUp,
        if (isInSuperset) {
            ExercisePopupAction.RemoveSuperset
        } else {
            ExercisePopupAction.AddSuperset
        },
        ExercisePopupAction.Barbell,
        ExercisePopupAction.DeleteExercise
    )
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismissRequest,
        containerColor = LiftingTheme.colors.surface,
        shape = LiftingTheme.shapes.medium
    ) {
        itemList.forEach { action ->
            DropdownMenuItem(
                onClick = {
                    onDismissRequest()
                    when (action) {
                        ExercisePopupAction.AddNote -> onAddNote()
                        ExercisePopupAction.WarmUp -> onAddWarmUpSets()
                        ExercisePopupAction.AddSuperset -> onAddToSuperset()
                        ExercisePopupAction.RemoveSuperset -> onRemoveFromSuperset()
                        ExercisePopupAction.Barbell -> onClickBarbell()
                        ExercisePopupAction.DeleteExercise -> onDeleteExercise()
                    }
                },
                text = {
                    Text(text = action.text.asString())
                },
                colors = MenuDefaults.itemColors(
                    textColor = LiftingTheme.colors.onBackground
                )
            )
        }
    }
}


/*
@Composable
internal fun ExercisePopupActions(
    isInSuperset: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteExercise: () -> Unit,
    onAddWarmUpSets: () -> Unit,
    onAddNote: () -> Unit,
    onClickBarbell: () -> Unit,
    onAddToSuperset: () -> Unit,
    onRemoveFromSuperset: () -> Unit,
    modifier: Modifier = Modifier,
    dropDownOffset: IntOffset = IntOffset.Zero
) {
    LiftingPopup(
        modifier = modifier,
        dropDownOffset = dropDownOffset,
        items = listOf(
            ExercisePopupAction.AddNote,
            ExercisePopupAction.WarmUp,
            if (isInSuperset) {
                ExercisePopupAction.RemoveSuperset
            } else {
                ExercisePopupAction.AddSuperset
            },
            ExercisePopupAction.Barbell,
            ExercisePopupAction.DeleteExercise
        ),
        itemDisplayText = { item -> item.text },
        onClick = { item ->
            onDismissRequest()
            when (item) {
                ExercisePopupAction.AddNote -> onAddNote()
                ExercisePopupAction.WarmUp -> onAddWarmUpSets()
                ExercisePopupAction.AddSuperset -> onAddToSuperset()
                ExercisePopupAction.RemoveSuperset -> onRemoveFromSuperset()
                ExercisePopupAction.Barbell -> onClickBarbell()
                ExercisePopupAction.DeleteExercise -> onDeleteExercise()
            }
        },
        onDismiss = onDismissRequest
    )
}
 */

private enum class ExercisePopupAction(
    val text: UiText
) {
    AddNote(
        UiText.StringResource(id = R.string.add_note)
    ),
    WarmUp(
        UiText.StringResource(id = R.string.warm_up_sets)
    ),
    AddSuperset(
        UiText.StringResource(id = R.string.add_to_superset)
    ),
    RemoveSuperset(
        UiText.StringResource(id = R.string.remove_from_superset)
    ),
    Barbell(
        UiText.StringResource(id = R.string.barbell)
    ),
    DeleteExercise(
        UiText.StringResource(id = R.string.delete_exercise)
    )
}
