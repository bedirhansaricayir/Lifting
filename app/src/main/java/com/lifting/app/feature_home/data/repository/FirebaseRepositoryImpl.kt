package com.lifting.app.feature_home.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.lifting.app.common.constants.Constants.Companion.IMAGES
import com.lifting.app.common.constants.Constants.Companion.PHOTO_URL
import com.lifting.app.common.constants.Constants.Companion.USERS
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import com.lifting.app.feature_home.domain.repository.FirebaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : FirebaseRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun getUserInfo(): Flow<Resource<UserInfo?>> = callbackFlow {
        val docRef = firestore.collection(USERS).document(currentUser?.uid.toString())

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

    override suspend fun addImageToFirebaseStorage(imageUri: Uri): Uri {
        return storage.reference.child(IMAGES).child("${currentUser?.uid}.jpg")
            .putFile(imageUri).await()
            .storage.downloadUrl.await()
    }


    override suspend fun addImageUrlToFirestore(downloadUrl: Uri): Boolean = try {
        currentUser?.uid?.let {  userId ->
            firestore.collection(USERS).document(userId).update(PHOTO_URL,downloadUrl.toString()).await()
        }
        true
    } catch (e: Exception) {
        false
    }

}