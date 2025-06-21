package com.lifting.app.feature.workout_editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseSetGroupNote
import com.lifting.app.core.ui.components.LiftingSwipeToDismissBox
import com.lifting.app.core.ui.components.LiftingTextField

/**
 * Created by bedirhansaricayir on 23.02.2025
 */

@Composable
internal fun NoteField(
    modifier: Modifier = Modifier,
    note: ExerciseSetGroupNote,
    onDeleteNote: () -> Unit,
    onChangeValue: (String) -> Unit
) {
    var mNoteText by remember {
        mutableStateOf(note.note ?: "")
    }

    fun handleTextChange(newValue: String) {
        mNoteText = newValue
        onChangeValue(newValue)
    }

    LiftingSwipeToDismissBox(
        modifier = modifier,
        containerColor = LiftingTheme.colors.background,
        onSwipeDelete = onDeleteNote
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LiftingTheme.colors.background)
                .padding(
                    horizontal = LiftingTheme.dimensions.large,
                    vertical = LiftingTheme.dimensions.small
                )
        ) {

            LiftingTextField(
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .clip(RoundedCornerShape(LiftingTheme.dimensions.medium))
                    .background(LiftingTheme.colors.surface),
                value = mNoteText,
                singleLine = false,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    handleTextChange(it)
                }
            )
        }
    }
}