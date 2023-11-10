package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DividerText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = textStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.LightGray,
            thickness = 1.dp
        )
    }
}