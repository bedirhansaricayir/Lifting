package com.lifting.app.feature_auth.presentation

import com.lifting.app.feature_auth.presentation.google_auth.SignInResult

sealed class AuthenticationEvent {
    object ToggleAuthenticationMode : AuthenticationEvent()

    class UsernameChanged(val username: String) : AuthenticationEvent()

    class EmailChanged(val emailAddress: String) : AuthenticationEvent()

    class PasswordChanged(val password: String) : AuthenticationEvent()

    class SignUpButtonClicked(val username: String, val email: String, val password: String) : AuthenticationEvent()

    class SignInButtonClicked(val email: String, val password: String) : AuthenticationEvent()

    object ErrorDismissed : AuthenticationEvent()

    object ToggleVisualTransformation : AuthenticationEvent()

    class OnSignInResultGoogle(val signInResult: SignInResult) : AuthenticationEvent()
}
