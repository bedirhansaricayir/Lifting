package com.lifting.app.feature_auth.domain.use_case

import com.google.firebase.auth.AuthResult
import com.lifting.app.core.util.Resource
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase() {

   suspend operator fun invoke(email: String, password: String): Flow<Resource<AuthResult>> {
        return handleAuthException { authRepository.emailAndPasswordSignIn(email, password) }
    }

}