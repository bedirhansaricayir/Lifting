package com.lifting.app.feature_auth.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun emailAndPasswordSignIn(
        email: String,
        password: String
    ): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

    override suspend fun emailAndPasswordSignUp(
        username: String,
        email: String,
        password: String
    ): AuthResult {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user?.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(username).build()
        )?.await()
        return result
    }

    override suspend fun sendEmailVerification(): Boolean = try {
        firebaseAuth.currentUser?.sendEmailVerification()?.await()
        true
    } catch (e: Exception) {
        false
    }

    override suspend fun reloadFirebaseUser(): Boolean = try {
        firebaseAuth.currentUser?.reload()?.await()
        true
    } catch (e: Exception) {
        false
    }


    override suspend fun sendPasswordResetEmail(email: String): Boolean = try {
        firebaseAuth.sendPasswordResetEmail(email).await()
        true
    } catch (e: Exception) {
        false
    }

    override fun signOut() = firebaseAuth.signOut()

}