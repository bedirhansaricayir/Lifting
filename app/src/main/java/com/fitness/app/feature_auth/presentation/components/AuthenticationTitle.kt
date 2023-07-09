package com.fitness.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.feature_auth.domain.model.AuthenticationMode
import com.fitness.app.ui.theme.White40

@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    textColor: Color = White40
) {
    Text(
        text = stringResource(
            if (authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.label_welcome_back
            } else {
                R.string.label_create_an_account
            }
        ),
        textAlign = TextAlign.Center,
        style = textStyle,
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}