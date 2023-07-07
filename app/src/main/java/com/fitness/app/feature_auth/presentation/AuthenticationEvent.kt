package com.fitness.app.feature_auth.presentation

sealed class AuthenticationEvent {
    object ToggleAuthenticationMode: AuthenticationEvent()

    class UsernameChanged (val username: String): AuthenticationEvent()

    class EmailChanged(val emailAddress: String): AuthenticationEvent()

    class PasswordChanged(val password: String): AuthenticationEvent()

    object Authenticate: AuthenticationEvent()

    object ErrorDismissed: AuthenticationEvent()


}
