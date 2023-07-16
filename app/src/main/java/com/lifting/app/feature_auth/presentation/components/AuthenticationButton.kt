package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.theme.Purple40
import com.lifting.app.theme.White40

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.background,
    shape: Dp = 25.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    enabledAuthentication: Boolean = false,
    isLoading: Boolean = false,
    onAuthenticate: () -> Unit
) {

    Button(
        modifier = modifier.height(40.dp),
        onClick = { onAuthenticate() },
        shape = RoundedCornerShape(shape),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor

        ),
        enabled = enabledAuthentication
    ) {
        if (isLoading) {
            CustomCircularProgressIndicatior(
                modifier = Modifier.size(20.dp),
            )
        } else {
            Text(
                text = stringResource(
                    if (authenticationMode ==
                        AuthenticationMode.SIGN_IN
                    ) {
                        R.string.action_sign_in
                    } else if (authenticationMode == AuthenticationMode.SIGN_UP) {
                        R.string.action_sign_up
                    } else if (authenticationMode == AuthenticationMode.VERIFICATION) {
                        R.string.action_verification
                    } else {
                        R.string.action_reset_password
                    }
                ),
                style = textStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationButtonPreview() {
    AuthenticationButton(authenticationMode = AuthenticationMode.SIGN_IN, backgroundColor = Purple40, contentColor = White40, isLoading = false, enabledAuthentication = true, modifier = Modifier.fillMaxWidth()) {

    }
}