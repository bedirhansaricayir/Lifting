package com.lifting.app.feature_auth.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.lifting.app.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?
    fun emailAndPasswordSignIn(email: String, password: String): Flow<Resource<AuthResult>>
    fun emailAndPasswordSignUp(username: String, email: String, password: String): Flow<Resource<AuthResult>>
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>

    fun signOut()
}