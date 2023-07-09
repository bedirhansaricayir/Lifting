package com.fitness.app.feature_auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.feature_auth.domain.model.AuthenticationMode
import com.fitness.app.feature_auth.data.model.AuthenticationState
import com.fitness.app.feature_auth.domain.model.PasswordRequirements
import com.fitness.app.feature_auth.presentation.components.AuthenticationButton
import com.fitness.app.feature_auth.presentation.components.AuthenticationTitle
import com.fitness.app.feature_auth.presentation.components.Requirement
import com.fitness.app.feature_auth.presentation.components.TextEntryModule
import com.fitness.app.feature_auth.presentation.components.ToggleAuthenticationMode
import com.fitness.app.ui.theme.grey50

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    onToggleModeClick: () -> Unit
) {

    SignUpScreenContent(
        modifier = modifier,
        authenticationMode = AuthenticationMode.SIGN_UP,
        username = authenticationState.username,
        email = authenticationState.email,
        password = authenticationState.password,
        completedPasswordRequirements = authenticationState.passwordRequirements,
        onUsernameChanged = { authenticationEvent(AuthenticationEvent.UsernameChanged(it)) },
        onEmailChanged = { authenticationEvent(AuthenticationEvent.EmailChanged(it)) },
        onPasswordChanged = { authenticationEvent(AuthenticationEvent.PasswordChanged(it)) },
        onAuthenticate = { authenticationEvent(AuthenticationEvent.Authenticate) },
        enableAuthentication = authenticationState.isFormValid(),
        onToggleMode = {
            authenticationEvent(AuthenticationEvent.ToggleAuthenticationMode)
            onToggleModeClick()
        },
        emailError  = authenticationState.emailError,
        isLoading = authenticationState.isLoading
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    username: String?,
    email: String?,
    password: String?,
    completedPasswordRequirements: List<PasswordRequirements>,
    onUsernameChanged: (username: String) -> Unit,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    enableAuthentication: Boolean,
    onToggleMode: () -> Unit,
    emailError: String?,
    isLoading: Boolean
) {
    val isPasswordValid = PasswordRequirements.values().all { requirement ->
        completedPasswordRequirements.contains(requirement)
    }
    var requirementsVisibility by remember { mutableStateOf(true) }
    var isEmailError by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
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
            textValue = username ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = onUsernameChanged,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
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
            imeAction = ImeAction.Next
        )
        AnimatedVisibility(
            visible = isEmailError,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally(),

        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(modifier = Modifier.align(Alignment.CenterVertically),text = emailError ?: "", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)

            }
        }
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
            visualTransformation = PasswordVisualTransformation(),
            isPasswordField = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
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

        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationMode,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            enabledAuthentication = enableAuthentication,
            isLoading = isLoading,
            onAuthenticate = {
                if (emailError != null) {
                    isEmailError = true
                }
                if (isPasswordValid && emailError == null){
                    requirementsVisibility = false
                    onAuthenticate()

                } else requirementsVisibility = true
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationMode(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
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