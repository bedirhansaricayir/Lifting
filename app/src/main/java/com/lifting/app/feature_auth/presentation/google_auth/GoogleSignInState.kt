package com.lifting.app.feature_auth.presentation.google_auth

import com.google.firebase.auth.FirebaseUser


data class GoogleSignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)
data class SignInResult(
    val data: FirebaseUser?,
    val errorMessage: String?
)