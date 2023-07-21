package com.lifting.app.feature_auth.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser: FirebaseUser?
    suspend fun emailAndPasswordSignIn(email: String, password: String): AuthResult
    suspend fun emailAndPasswordSignUp(username: String, email: String, password: String): AuthResult
    suspend fun sendEmailVerification(): Boolean
    suspend fun reloadFirebaseUser(): Boolean
    suspend fun sendPasswordResetEmail(email: String): Boolean

    suspend fun addUserToFirestore()
    fun signOut()
}