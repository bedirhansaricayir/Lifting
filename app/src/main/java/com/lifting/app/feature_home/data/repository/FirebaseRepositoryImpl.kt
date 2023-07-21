package com.lifting.app.feature_home.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lifting.app.common.constants.Constants.Companion.USERS
import com.lifting.app.feature_home.domain.model.UserInfo
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FirebaseRepository {
    override suspend fun getUserInfo(userId: String): UserInfo? {
        val docRef = firestore.collection(USERS).document(userId)
        val document = docRef.get().await()

        if (document.exists()) {
            val displayName = document.getString("displayName") ?: ""
            val email = document.getString("email") ?: ""
            val photoUrl = document.getString("photoUrl") ?: ""

            return UserInfo(displayName, email, photoUrl)
        }
        return null
    }
}