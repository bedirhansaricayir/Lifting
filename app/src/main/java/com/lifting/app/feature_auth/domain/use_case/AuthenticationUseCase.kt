package com.lifting.app.feature_auth.domain.use_case


import com.lifting.app.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun provideCurrentUser() = authRepository.currentUser

    fun signOut() = authRepository.signOut()

}