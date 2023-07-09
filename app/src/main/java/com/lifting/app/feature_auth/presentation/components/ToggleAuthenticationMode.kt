package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.theme.White40

@Composable
fun ToggleAuthenticationMode(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    toggleAuthentication: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                if (authenticationMode ==
                    AuthenticationMode.SIGN_IN
                ) {
                    R.string.action_need_account
                } else {
                    R.string.action_already_have_account
                }
            ),
            style = MaterialTheme.typography.labelSmall,
            color = White40
        )
        TextButton(onClick = { toggleAuthentication() }) {
            Text(
                text = stringResource(
                    if (authenticationMode == AuthenticationMode.SIGN_IN) {
                        R.string.action_sign_in_text_button
                    } else {
                        R.string.action_sign_up_text_button
                    }
                ),
                style = MaterialTheme.typography.labelSmall,
                color = White40,
                fontWeight = FontWeight.Bold

            )
        }
    }
}