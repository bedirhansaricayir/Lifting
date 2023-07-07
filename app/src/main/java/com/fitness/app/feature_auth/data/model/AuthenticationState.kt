package com.fitness.app.feature_auth.data.model

import androidx.annotation.StringRes
import com.fitness.app.R

data class AuthenticationState(
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    fun isFormValid(): Boolean {
        return password?.isNotEmpty() == true &&
                email?.isNotEmpty() == true &&
                username?.isNotEmpty() == true &&
                (authenticationMode == AuthenticationMode.SIGN_IN
                        || passwordRequirements.containsAll(
                    PasswordRequirements.values().map { it.toString() }))
    }
}