package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.R


@Composable
fun ForgotPasswordScreenText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    subTitleStyle: TextStyle = MaterialTheme.typography.labelMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    subTitleTextColor: Color = Color.LightGray,
    isSubTitle: Boolean = false

) {
    Text(
        text = stringResource(
            if (isSubTitle) {
                R.string.label_forgot_password_subtitle
            } else {
                R.string.label_forgot_password
            }
        ),
        style = if (isSubTitle) subTitleStyle else textStyle,
        color = if (isSubTitle) subTitleTextColor else textColor,
        textAlign = TextAlign.Center,
        fontWeight = if (isSubTitle) FontWeight.Normal else FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}