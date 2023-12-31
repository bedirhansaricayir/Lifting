package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ForgotPasswordText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onForgotPasswordClick: () -> Unit
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                onForgotPasswordClick()
            },
        style = textStyle,
        color = textColor,
        textAlign = TextAlign.End
    )
}