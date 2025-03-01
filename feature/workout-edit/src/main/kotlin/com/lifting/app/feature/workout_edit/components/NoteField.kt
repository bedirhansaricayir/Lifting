package com.lifting.app.feature.workout_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingTextField
import com.lifting.app.core.ui.extensions.lighterColor

/**
 * Created by bedirhansaricayir on 23.02.2025
 */

@Composable
internal fun NoteField(
    modifier: Modifier = Modifier,
    note: String,
    onDeleteNote: () -> Unit,
    onChangeValue: (String) -> Unit
) {
    var mNoteText by remember {
        mutableStateOf(note)
    }

    fun handleTextChange(newValue: String) {
        mNoteText = newValue
        onChangeValue(newValue)
    }

    SwipeableContainerComponent(
        modifier = modifier,
        bgColor = LiftingTheme.colors.background,
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
                    .background(LiftingTheme.colors.background.lighterColor(0.10f)),
                value = mNoteText,
                singleLine = false,
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