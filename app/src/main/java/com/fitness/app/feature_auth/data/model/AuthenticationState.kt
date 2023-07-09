package com.fitness.app.feature_auth.data.model

import com.fitness.app.feature_auth.domain.model.AuthenticationMode
import com.fitness.app.feature_auth.domain.model.PasswordRequirements

data class AuthenticationState(
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirements> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: String? = null
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