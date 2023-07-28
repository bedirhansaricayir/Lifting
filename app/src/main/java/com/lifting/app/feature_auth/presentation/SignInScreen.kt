package com.lifting.app.feature_auth.presentation

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.lifting.app.feature_auth.presentation.components.GoogleButton
import com.lifting.app.feature_auth.presentation.components.TextEntryModule
import com.lifting.app.feature_auth.presentation.components.ToggleAuthenticationMode
import com.lifting.app.feature_auth.presentation.forgot_password.ForgotPasswordModalBottomSheetContent
import com.lifting.app.feature_auth.presentation.forgot_password.ForgotPasswordState
import com.lifting.app.feature_auth.presentation.google_auth.GoogleSignInState
import com.lifting.app.theme.grey50

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    googleSignInState: GoogleSignInState,
    forgotPasswordState: ForgotPasswordState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    onToggleModeClick: () -> Unit,
    onGoogleSignInButtonClicked: () -> Unit,
    onSignInNavigate: () -> Unit,
    onVerificationRequired: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = googleSignInState.signInError) {
        googleSignInState.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = googleSignInState.isSignInSuccessful) {
        if (googleSignInState.isSignInSuccessful) {
            authenticationEvent(AuthenticationEvent.OnSignInSuccessful(true))
            onSignInNavigate()
        }
    }
    LaunchedEffect(key1 = authenticationState.error) {
        authenticationState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            authenticationEvent(AuthenticationEvent.ErrorDismissed)
        }
    }
    LaunchedEffect(key1 = forgotPasswordState.error) {
        forgotPasswordState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            authenticationEvent(AuthenticationEvent.ForgotPasswordErrorDismissed)
        }
    }

    LaunchedEffect(key1 = authenticationState.authResult) {
        val authResult = authenticationState.authResult
        if (authResult != null && authResult.user?.isEmailVerified == true) {
            authenticationEvent(AuthenticationEvent.OnSignInSuccessful(true))
            onSignInNavigate()
        }
        if (authResult != null && authResult.user?.isEmailVerified == false) {
            authenticationEvent(AuthenticationEvent.OnVerificationRequired)
            onVerificationRequired()
        }
    }

    SignInScreenContent(
        modifier = modifier,
        authenticationMode = AuthenticationMode.SIGN_IN,
        forgotPasswordState = forgotPasswordState,
        authenticationState = authenticationState,
        googleSignInState = googleSignInState,
        onEmailChanged = { authenticationEvent(AuthenticationEvent.EmailChanged(it)) },
        onPasswordChanged = { authenticationEvent(AuthenticationEvent.PasswordChanged(it)) },
        onForgotPasswordEmailChanged = { authenticationEvent(AuthenticationEvent.ForgotPasswordEmailChanged(it)) },
        onSignInButtonClicked = { email, password ->
            authenticationEvent(AuthenticationEvent.SignInButtonClicked(email, password))
        },
        onToggleMode = {
            authenticationEvent(AuthenticationEvent.ToggleAuthenticationMode)
            onToggleModeClick()
        },
        onTrailingIconClick = { authenticationEvent(AuthenticationEvent.ToggleVisualTransformation) },
        onGoogleSignInButtonClicked = {
            onGoogleSignInButtonClicked()
            authenticationEvent(AuthenticationEvent.OnGoogleButtonDisabled)
        },
        onResetPasswordSend = { authenticationEvent(AuthenticationEvent.OnResetPasswordRequest(it)) },
        clearStateWhenResetPasswordSend = { authenticationEvent(AuthenticationEvent.ClearForgotPasswordState) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    forgotPasswordState: ForgotPasswordState,
    authenticationState: AuthenticationState,
    googleSignInState: GoogleSignInState,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onForgotPasswordEmailChanged: (email: String) -> Unit,
    onSignInButtonClicked: (String, String) -> Unit,
    onToggleMode: () -> Unit,
    onTrailingIconClick: () -> Unit,
    onGoogleSignInButtonClicked: () -> Unit,
    onResetPasswordSend: (email: String) -> Unit,
    clearStateWhenResetPasswordSend: () -> Unit

) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var openBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = forgotPasswordState.isSend) {
        if (forgotPasswordState.isSend) {
            openBottomSheet = !openBottomSheet
            clearStateWhenResetPasswordSend()
        }
    }
    if (openBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.systemBarsPadding(),
            onDismissRequest = {
                openBottomSheet = !openBottomSheet
                clearStateWhenResetPasswordSend()
            },
            sheetState = modalBottomSheetState,
            containerColor = grey50,
            scrimColor = grey50.copy(alpha = 0.7f)
        ) {
            ForgotPasswordModalBottomSheetContent(
                modifier = Modifier,
                forgotPasswordState = forgotPasswordState,
                onForgotPasswordEmailChanged = onForgotPasswordEmailChanged,
                onResetPasswordSend = onResetPasswordSend
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50)
            .padding(16.dp)
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
            textValue = authenticationState.email ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onEmailChanged,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onTrailingIconClick = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextEntryModule(
            text = stringResource(id = R.string.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourPassword),
            leadingIcon = Icons.Default.Lock,
            textValue = authenticationState.password ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onPasswordChanged,
            visualTransformation = if (authenticationState.isPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            trailingIcon = if (authenticationState.isPasswordShown) R.drawable.icon_visibility else R.drawable.icon_visibility_off,
            onTrailingIconClick = onTrailingIconClick
        )
        ForgotPasswordText(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.label_forgot_password),
            onForgotPasswordClick = { openBottomSheet = !openBottomSheet })
        Spacer(modifier = Modifier.height(8.dp))
        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationMode,
            enabledAuthentication = authenticationState.isLoginFormValid(),
            onAuthenticate = {
                onSignInButtonClicked(
                    authenticationState.email!!,
                    authenticationState.password!!
                )
            },
            isLoading = authenticationState.isLoading
        )
        Spacer(modifier = Modifier.weight(2f))

        DividerText(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.label_sign_in_to_account)
        )
        Spacer(modifier = Modifier.weight(1f))
        GoogleButton(
            text = stringResource(id = R.string.continue_with_google),
            icon = R.drawable.ic_google_logo,
            onClicked = onGoogleSignInButtonClicked,
            isClickable = googleSignInState.googleButtonClickableState
        )
        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationMode(
            modifier = Modifier
                .fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = onToggleMode
        )
    }
}

