package com.fitness.app.feature_auth.presentation

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
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
import com.fitness.app.feature_auth.data.model.AuthenticationState
import com.fitness.app.feature_auth.data.model.PasswordRequirements
import com.fitness.app.feature_auth.presentation.components.AuthenticationButton
import com.fitness.app.feature_auth.presentation.components.AuthenticationTitle
import com.fitness.app.feature_auth.presentation.components.Requirement
import com.fitness.app.feature_auth.presentation.components.TextEntryModule
import com.fitness.app.ui.theme.grey50

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    completedPasswordRequirements: List<PasswordRequirements> = authenticationState.passwordRequirements
) {
    var requirementsVisibility by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(
            authenticationMode = authenticationState.authenticationMode,
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
            onValueChanged = { authenticationEvent(AuthenticationEvent.UsernameChanged(it)) },
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
            textValue = authenticationState.email ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = { authenticationEvent(AuthenticationEvent.EmailChanged(it)) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        TextEntryModule(
            text = stringResource(id = R.string.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 5.dp),
            hint = stringResource(id = R.string.EnterYourPassword),
            leadingIcon = Icons.Default.Lock,
            textValue = authenticationState.password ?: "",
            cursorColor = MaterialTheme.colorScheme.primary,
            onValueChanged = { authenticationEvent(AuthenticationEvent.PasswordChanged(it)) },
            visualTransformation = PasswordVisualTransformation(),
            isPasswordField = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
        AnimatedVisibility(visible = requirementsVisibility) {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),contentAlignment = Alignment.CenterStart){
                PasswordRequirement(satisfiedRequirements = completedPasswordRequirements)
            }
        }

        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationState.authenticationMode,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            enabled = authenticationState.isFormValid()
        ) {
            requirementsVisibility = !requirementsVisibility
        }
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