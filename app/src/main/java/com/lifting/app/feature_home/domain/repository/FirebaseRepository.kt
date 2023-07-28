package com.lifting.app.feature_home.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    val currentUser: FirebaseUser?

    suspend fun getUserInfo(): Flow<Resource<UserInfo?>>

    suspend fun addImageToFirebaseStorage(imageUri: Uri): Uri

    suspend fun addImageUrlToFirestore(downloadUrl: Uri): Boolean
}