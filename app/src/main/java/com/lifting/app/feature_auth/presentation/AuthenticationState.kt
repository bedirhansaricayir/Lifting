package com.lifting.app.feature_auth.presentation

import com.google.firebase.auth.AuthResult
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.domain.model.PasswordRequirements

data class AuthenticationState(
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirements> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: Boolean = false,
    val isPasswordShown: Boolean = false,
    val authResult: AuthResult? = null,
    val sendEmailVerification: Boolean = false,
    val isEmailVerified: Boolean = false,
    val isUserReloaded: Boolean = false,
) {
    fun isFormValid(): Boolean {
        return password?.isNotEmpty() == true &&
                email?.isNotEmpty() == true &&
                username?.isNotEmpty() == true &&
                (authenticationMode == AuthenticationMode.SIGN_IN
                        || passwordRequirements.containsAll(
                    PasswordRequirements.values().toList()))
    }

    fun isLoginFormValid(): Boolean {
        return password?.isNotEmpty() == true &&
                email?.isNotEmpty() == true
    }
}