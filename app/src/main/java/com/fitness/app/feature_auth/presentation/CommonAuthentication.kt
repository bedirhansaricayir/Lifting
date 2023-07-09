package com.fitness.app.feature_auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fitness.app.feature_auth.domain.model.AuthenticationMode

@Composable
fun CommonAuthentication(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    onToggleModeClick: () -> Unit
) {
    when(authenticationState.authenticationMode) {
        AuthenticationMode.SIGN_IN -> {
            SignInScreen(modifier = modifier, authenticationState = authenticationState, authenticationEvent = authenticationEvent, onToggleModeClick = onToggleModeClick)
        }
        AuthenticationMode.SIGN_UP -> {
            SignUpScreen(modifier = modifier, authenticationState = authenticationState, authenticationEvent = authenticationEvent, onToggleModeClick = onToggleModeClick)
        }
    }
}