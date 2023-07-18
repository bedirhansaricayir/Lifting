package com.lifting.app.feature_auth.domain.use_case

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.lifting.app.core.util.Resource
import com.lifting.app.feature_auth.domain.model.AuthException
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthenticationUseCase @Inject constructor(private val authRepository: AuthRepository) {


    fun provideCurrentUser() = authRepository.currentUser

    suspend fun emailPasswordSignIn(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = authRepository.emailAndPasswordSignIn(email, password)
            emit(Resource.Success(result))
        }.catch { throwable ->
            val signInException = handleSignInException(throwable)
            emit(Resource.Error(signInException.message))
        }
    }

    suspend fun emailPasswordSignUp(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = authRepository.emailAndPasswordSignUp(username, email, password)
            emit(Resource.Success(result))
        }.catch { throwable ->
            val signUpException = handleSignInException(throwable)
            emit(Resource.Error(signUpException.message))
        }
    }

    suspend fun sendEmailVerification(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            authRepository.sendEmailVerification()
            emit(Resource.Success(true))
        }.catch { throwable ->
            val sendEmailException = handleSignInException(throwable)
            emit(Resource.Error(sendEmailException.message))
        }
    }

    suspend fun reloadFirebaseUser(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            authRepository.reloadFirebaseUser()
            emit(Resource.Success(true))
        }.catch { throwable ->
            val reloadUserException = handleSignInException(throwable)
            emit(Resource.Error(reloadUserException.message))
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            authRepository.sendPasswordResetEmail(email)
            emit(Resource.Success(true))
        }.catch { throwable ->
            val sendPasswordResetException = handleSignInException(throwable)
            emit(Resource.Error(sendPasswordResetException.message))
        }
    }

    fun signOut() = authRepository.signOut()

    private fun handleSignInException(throwable: Throwable): AuthException {
        return when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> AuthException.FirebaseAuthInvalidCredentialsException(
                throwable.errorCode
            ).handleErrorCode()

            is FirebaseAuthInvalidUserException -> AuthException.FirebaseAuthInvalidUserException(
                throwable.errorCode
            ).handleErrorCode()

            is FirebaseNetworkException -> AuthException.FirebaseNetworkException
            is FirebaseTooManyRequestsException -> AuthException.FirebaseTooManyRequestsException
            is FirebaseAuthUserCollisionException -> AuthException.FirebaseAuthUserCollisionException
            else -> throw throwable
        }
    }

}