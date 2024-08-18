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
import com.lifting.app.core.ui.extensions.lighterColor

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

@Composable
fun LiftingTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: CornerBasedShape = LiftingTheme.shapes.small,
    placeholder: String? = null

    ) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = LiftingTheme.colors.onBackground,
            focusedTextColor = LiftingTheme.colors.onBackground,
            unfocusedContainerColor = LiftingTheme.colors.background.lighterColor(0.1f),
            focusedContainerColor = LiftingTheme.colors.background.lighterColor(0.1f),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = shape,
        placeholder = placeholder?.let { { Text(text = it) } }
    )
}


