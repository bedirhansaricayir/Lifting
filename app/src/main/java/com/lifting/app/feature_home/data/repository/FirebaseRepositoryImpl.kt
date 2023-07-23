package com.lifting.app.feature_home.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.lifting.app.common.constants.Constants.Companion.USERS
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    override suspend fun getUserInfo(userId: String): Flow<Resource<UserInfo?>> = callbackFlow {
        val docRef = firestore.collection(USERS).document(userId)

        val snapshotListener = docRef.addSnapshotListener { snapshot, e ->
            val userInfoResponse = if (snapshot != null) {
                val userInfo = snapshot.toObject(UserInfo::class.java)
                Resource.Success(userInfo)
            } else {
                Resource.Error(e?.message.toString())
            }
            trySend(userInfoResponse)
        }
        awaitClose { snapshotListener.remove() }
    }
}