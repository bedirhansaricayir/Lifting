package com.lifting.app.feature_auth.presentation.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.presentation.components.AuthenticationButton
import com.lifting.app.feature_auth.presentation.components.ForgotPasswordScreenText
import com.lifting.app.feature_auth.presentation.components.TextEntryModule

@Composable
fun ForgotPasswordModalBottomSheetContent(
    modifier: Modifier = Modifier,
    forgotPasswordState: ForgotPasswordState,
    onForgotPasswordEmailChanged: (email: String) -> Unit,
    onResetPasswordSend: (email: String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ForgotPasswordScreenText()
        ForgotPasswordScreenText(isSubTitle = true)
        TextEntryModule(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourEmail),
            leadingIcon = Icons.Default.Email,
            textValue = forgotPasswordState.email ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onForgotPasswordEmailChanged,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            onTrailingIconClick = null
        )
        AuthenticationButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            authenticationMode = AuthenticationMode.FORGOT_PASSWORD,
            enabledAuthentication = forgotPasswordState.isValid(),
            isLoading = forgotPasswordState.isLoading,
            onAuthenticate = { onResetPasswordSend(forgotPasswordState.email!!) }
        )
    }
}