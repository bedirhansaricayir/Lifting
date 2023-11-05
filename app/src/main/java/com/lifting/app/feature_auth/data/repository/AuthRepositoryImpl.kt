package com.lifting.app.feature_auth.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.lifting.app.common.constants.UserFieldContants.CREATED_AT
import com.lifting.app.common.constants.UserFieldContants.DEFAULT_AVATAR_STORAGE
import com.lifting.app.common.constants.UserFieldContants.DISPLAY_NAME
import com.lifting.app.common.constants.UserFieldContants.EMAIL
import com.lifting.app.common.constants.UserFieldContants.IS_PREMIUM
import com.lifting.app.common.constants.UserFieldContants.PHOTO_URL
import com.lifting.app.common.constants.UserFieldContants.USERS
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addUserToFirestore() {
        firebaseAuth.currentUser?.apply {
            val user = toUser()
            firestore.collection(USERS).document(uid).set(user).await()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun FirebaseUser.toUser() = mapOf(
        DISPLAY_NAME to displayName,
        EMAIL to email,
        IS_PREMIUM to false,
        PHOTO_URL to userPhoto(),
        CREATED_AT to localDate()
    )

    private fun FirebaseUser.userPhoto(): String {
        return photoUrl?.toString() ?: DEFAULT_AVATAR_STORAGE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FirebaseUser.localDate(): String {
        return LocalDate.now().toLocaleFormat()
    }

}