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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode

@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    subTitleStyle: TextStyle = MaterialTheme.typography.labelMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    subTitleColor: Color = Color.LightGray,
    isSubTitle: Boolean = false,
) {
    Text(
        text = stringResource(
            if (isSubTitle){
                R.string.label_verification_screen_subtitle
            } else if (authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.label_welcome_back
            } else if (authenticationMode == AuthenticationMode.SIGN_UP) {
                R.string.label_create_an_account
            } else {
                R.string.label_verification_screen
            }
        ),
        textAlign = TextAlign.Center,
        style = if (isSubTitle) subTitleStyle else textStyle,
        color = if (isSubTitle) subTitleColor else textColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}