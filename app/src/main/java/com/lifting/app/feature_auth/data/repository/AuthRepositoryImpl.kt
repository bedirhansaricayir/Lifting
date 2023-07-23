package com.lifting.app.feature_auth.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.lifting.app.common.constants.Constants.Companion.DISPLAY_NAME
import com.lifting.app.common.constants.Constants.Companion.EMAIL
import com.lifting.app.common.constants.Constants.Companion.PHOTO_URL
import com.lifting.app.common.constants.Constants.Companion.USERS
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
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

    override suspend fun addUserToFirestore() {
        firebaseAuth.currentUser?.apply {
            val user = toUser()
            firestore.collection(USERS).document(uid).set(user).await()
        }
    }
    private fun FirebaseUser.toUser() = mapOf(
        DISPLAY_NAME to displayName,
        EMAIL to email,
        PHOTO_URL to photoUrl?.toString()
    )

}