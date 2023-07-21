package com.lifting.app.feature_auth.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.lifting.app.feature_auth.domain.model.PasswordRequirements
import com.lifting.app.feature_auth.presentation.components.AuthenticationButton
import com.lifting.app.feature_auth.presentation.components.AuthenticationTitle
import com.lifting.app.feature_auth.presentation.components.Requirement
import com.lifting.app.feature_auth.presentation.components.TextEntryModule
import com.lifting.app.feature_auth.presentation.components.ToggleAuthenticationMode
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey50

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    onToggleModeClick: () -> Unit,
    onSignUpNavigate: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = authenticationState.sendEmailVerification) {
        if (authenticationState.sendEmailVerification) {
            onSignUpNavigate()
        }
    }
    LaunchedEffect(key1 = authenticationState.error) {
        if (authenticationState.error != null) {
            Toast.makeText(context,authenticationState.error,Toast.LENGTH_LONG).show()
            authenticationEvent(AuthenticationEvent.ErrorDismissed)
        }
    }

    SignUpScreenContent(
        modifier = modifier,
        authenticationMode = AuthenticationMode.SIGN_UP,
        authenticationState = authenticationState,
        completedPasswordRequirements = authenticationState.passwordRequirements,
        onUsernameChanged = { authenticationEvent(AuthenticationEvent.UsernameChanged(it)) },
        onEmailChanged = { authenticationEvent(AuthenticationEvent.EmailChanged(it)) },
        onPasswordChanged = { authenticationEvent(AuthenticationEvent.PasswordChanged(it)) },
        onAuthenticate = { username, email, password ->
            authenticationEvent(AuthenticationEvent.SignUpButtonClicked(username, email, password))
        },
        onToggleMode = {
            authenticationEvent(AuthenticationEvent.ToggleAuthenticationMode)
            onToggleModeClick()
        },
        onTrailingIconClick = { authenticationEvent(AuthenticationEvent.ToggleVisualTransformation) }
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    authenticationState: AuthenticationState,
    completedPasswordRequirements: List<PasswordRequirements>,
    onUsernameChanged: (username: String) -> Unit,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: (String, String, String) -> Unit,
    onToggleMode: () -> Unit,
    onTrailingIconClick: () -> Unit
) {
    val isPasswordValid = PasswordRequirements.values().all { requirement ->
        completedPasswordRequirements.contains(requirement)
    }
    var requirementsVisibility by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50)
            .padding(16.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        AuthenticationTitle(
            authenticationMode = authenticationMode,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextEntryModule(
            text = stringResource(id = R.string.Username),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourName),
            leadingIcon = Icons.Default.Person,
            textValue = authenticationState.username ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onUsernameChanged,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            onTrailingIconClick = null,
            isUsernameField = true
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            trailingIcon = R.drawable.icon_check_circle,
            trailingIconColor = if (!authenticationState.emailError && authenticationState.email?.isNotEmpty() == true) MaterialTheme.colorScheme.primary else grey10,
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
        AnimatedVisibility(visible = requirementsVisibility) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart
            ) {
                PasswordRequirement(satisfiedRequirements = completedPasswordRequirements)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationMode,
            enabledAuthentication = authenticationState.isFormValid(),
            isLoading = authenticationState.isLoading,
            onAuthenticate = {
                if (isPasswordValid && !authenticationState.emailError) {
                    requirementsVisibility = false
                    onAuthenticate(authenticationState.username!!, authenticationState.email!!, authenticationState.password!!)

                } else requirementsVisibility = !isPasswordValid
            }
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

@Composable
fun PasswordRequirement(
    modifier: Modifier = Modifier,
    satisfiedRequirements: List<PasswordRequirements>
) {
    Column(
        modifier = modifier
    ) {
        PasswordRequirements.values().forEach { requirement ->
            Requirement(
                message = stringResource(id = requirement.label),
                satisfied = satisfiedRequirements.contains(requirement)
            )
        }
    }
}