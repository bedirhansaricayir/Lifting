package com.lifting.app.core.ui.components

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 05.02.2025
 */

@Composable
fun LiftingTextButton(
    text: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(contentColor = LiftingTheme.colors.primary),
    style: TextStyle = LiftingTheme.typography.button,
    onClick: () -> Unit = { },
) {
    TextButton(
        modifier = modifier,
        colors = colors,
        shape = LiftingTheme.shapes.medium,
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = style
        )
    }
}