package com.lifting.app.feature_home.domain.repository

import com.lifting.app.feature_home.domain.model.UserInfo

interface FirebaseRepository {

    suspend fun getUserInfo(userId: String): UserInfo?
}