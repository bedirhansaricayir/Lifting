package com.lifting.app.feature_auth.domain.use_case

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReloadUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase() {

    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return handleAuthException { authRepository.reloadFirebaseUser() }
    }
}