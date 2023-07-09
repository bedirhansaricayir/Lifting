package com.lifting.app.feature_auth.data.repository

import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl: AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        delay(1000)
        return true
    }

    override suspend fun register(email: String, password: String): Boolean {
        delay(1000)
        return true
    }

}