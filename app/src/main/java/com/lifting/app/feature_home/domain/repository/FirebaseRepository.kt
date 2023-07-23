package com.lifting.app.feature_home.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    val currentUser: FirebaseUser?

    suspend fun getUserInfo(userId: String): Flow<Resource<UserInfo?>>
}