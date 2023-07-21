package com.lifting.app.feature_auth.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase() {

    suspend operator fun invoke(email: String): Flow<Resource<Boolean>> {
        return handleAuthException { authRepository.sendPasswordResetEmail(email) }
    }
}