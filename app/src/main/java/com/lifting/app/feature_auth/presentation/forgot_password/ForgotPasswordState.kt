package com.lifting.app.feature_auth.presentation.forgot_password

data class ForgotPasswordState(
    val email: String? = null,
    val error: String? = null,
    val isEmailValid: Boolean = false,
    val isLoading: Boolean = false,
    val isSend: Boolean = false
) {
    fun isValid(): Boolean {
        return email?.isNotEmpty() == true && error == null && isEmailValid
    }
}