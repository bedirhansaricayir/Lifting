package com.lifting.app.feature_auth.presentation.google_auth

import com.google.firebase.auth.FirebaseUser


data class GoogleSignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val googleButtonClickableState: Boolean = true
)
data class SignInResult(
    val data: FirebaseUser?,
    val errorMessage: String?
)