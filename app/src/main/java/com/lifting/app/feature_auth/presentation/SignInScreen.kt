package com.lifting.app.feature_auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.presentation.components.AuthenticationButton
import com.lifting.app.feature_auth.presentation.components.AuthenticationTitle
import com.lifting.app.feature_auth.presentation.components.DividerText
import com.lifting.app.feature_auth.presentation.components.ForgotPasswordText
import com.lifting.app.feature_auth.presentation.components.SwipeButton
import com.lifting.app.feature_auth.presentation.components.TextEntryModule
import com.lifting.app.feature_auth.presentation.components.ToggleAuthenticationMode
import com.lifting.app.theme.grey50
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    onToggleModeClick: () -> Unit
) {
    SignInScreenContent(
        modifier = modifier,
        authenticationMode = AuthenticationMode.SIGN_IN,
        email = authenticationState.email,
        password = authenticationState.password,
        onEmailChanged = { authenticationEvent(AuthenticationEvent.EmailChanged(it)) },
        onPasswordChanged = { authenticationEvent(AuthenticationEvent.PasswordChanged(it)) },
        onAuthenticate = { authenticationEvent(AuthenticationEvent.Authenticate) },
        enableAuthentication = authenticationState.isLoginFormValid(),
        onToggleMode = {
            authenticationEvent(AuthenticationEvent.ToggleAuthenticationMode)
            onToggleModeClick()
        },
        isLoading = authenticationState.isLoading,
        isPasswordShown = authenticationState.isPasswordShown,
        onTrailingIconClick = { authenticationEvent(AuthenticationEvent.ToggleVisualTransformation) },
        onForgotPasswordClick = {}
    )
}

@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    email: String?,
    password: String?,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    enableAuthentication: Boolean,
    onToggleMode: () -> Unit,
    isLoading: Boolean,
    isPasswordShown: Boolean,
    onTrailingIconClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50)
            .padding(8.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        AuthenticationTitle(
            authenticationMode = authenticationMode,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextEntryModule(
            text = stringResource(id = R.string.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourEmail),
            leadingIcon = Icons.Default.Email,
            textValue = email ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onEmailChanged,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onTrailingIconClick = null
        )
        TextEntryModule(
            text = stringResource(id = R.string.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourPassword),
            leadingIcon = Icons.Default.Lock,
            textValue = password ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onPasswordChanged,
            visualTransformation = if (isPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            trailingIcon = if (isPasswordShown) R.drawable.icon_visibility else R.drawable.icon_visibility_off,
            onTrailingIconClick = onTrailingIconClick
        )
        ForgotPasswordText(modifier = Modifier.padding(8.dp), text = stringResource(id = R.string.label_forgot_password), onForgotPasswordClick = onForgotPasswordClick)

        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationMode,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            enabledAuthentication = enableAuthentication,
            onAuthenticate = onAuthenticate,
            isLoading = isLoading
        )

        //SwipeButtonSample(AuthenticationMode.SIGN_IN,enableAuthentication,true)
        Spacer(modifier = Modifier.weight(1f))

        DividerText(modifier = Modifier.padding(horizontal = 16.dp), text = stringResource(id = R.string.label_sign_in_to_account))
        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationMode(
            modifier = Modifier
                .fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = onToggleMode
        )
    }
}

@Composable
fun SwipeButtonSample(authenticationMode: AuthenticationMode, enableAuthenticationMode: Boolean, isFail: Boolean) {
    val coroutineScope = rememberCoroutineScope()
    val (isComplete, setIsComplete) = remember {
        mutableStateOf(false)
    }

    SwipeButton(
        authenticationMode = authenticationMode,
        enableAuthenticationMode = enableAuthenticationMode,
        isComplete = isComplete,
        isFail = isFail ,
        onSwipe = {
            coroutineScope.launch {
                delay(2000)
                setIsComplete(true)
            }
        },
    )
}