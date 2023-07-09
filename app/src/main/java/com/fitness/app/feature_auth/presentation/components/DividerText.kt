package com.fitness.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey10

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
            color = grey10,
            thickness = 1.dp
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = textStyle,
            color = White40
        )
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            color = grey10,
            thickness = 1.dp
        )
    }
}