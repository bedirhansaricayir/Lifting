package com.lifting.app.feature_auth.presentation.google_auth


data class GoogleSignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)