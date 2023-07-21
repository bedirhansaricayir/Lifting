package com.lifting.app.feature_auth.domain.use_case

import com.google.firebase.auth.AuthResult
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase() {

    suspend operator fun invoke(username: String, email: String, password: String): Flow<Resource<AuthResult>> {
        return handleAuthException { authRepository.emailAndPasswordSignUp(username, email, password) }
    }
}