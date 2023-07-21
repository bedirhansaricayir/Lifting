package com.lifting.app.feature_auth.domain.use_case

import com.lifting.app.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AddUserToFirestoreUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        return authRepository.addUserToFirestore()
    }
}