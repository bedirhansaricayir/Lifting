package com.lifting.app.feature_home.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun UserInfoText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    subTitleStyle: TextStyle = MaterialTheme.typography.labelSmall,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    subTitleColor: Color = Color.LightGray,
    isSubTitle: Boolean = false,
){
    Text(
        text = text,
        style = if (isSubTitle) subTitleStyle else textStyle,
        color = if (isSubTitle) subTitleColor else textColor,
        modifier = modifier,
        fontWeight = if (isSubTitle) FontWeight.Normal else FontWeight.Bold

    )
}