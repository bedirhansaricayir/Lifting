package com.lifting.app.feature.workout_edit.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.ui.R


/**
 * Created by bedirhansaricayir on 23.02.2025
 */

@Composable
internal fun ExercisePopupActions(
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteExercise: () -> Unit,
    onAddNote: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isExpanded,
        onDismissRequest = onDismissRequest
    ) {
        /*DropdownMenuItem(
            onClick = {
                onDismissRequest()
                onAddNote()
            },
            text = {
                Text("Add Note")
            }
        )*/

        DropdownMenuItem(
            onClick = {
                onDismissRequest()
                onDeleteExercise()
            },
            text = {
                Text(text = stringResource(id = R.string.delete_exercise))
            }
        )
    }
}