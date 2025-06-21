package com.lifting.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

@Composable
fun LiftingTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: CornerBasedShape = LiftingTheme.shapes.medium,
    placeholder: String? = null
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else maxLines,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = LiftingTheme.colors.onBackground,
            focusedTextColor = LiftingTheme.colors.onBackground,
            unfocusedContainerColor = LiftingTheme.colors.surface,
            focusedContainerColor = LiftingTheme.colors.surface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = LiftingTheme.colors.primary,
        ),
        shape = shape,
        placeholder = placeholder?.let {
            {
                Text(
                    text = it,
                    color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
                )
            }
        }
    )
}
