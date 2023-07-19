package com.lifting.app.feature_auth.domain.use_case

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.lifting.app.core.util.Resource
import com.lifting.app.feature_auth.domain.model.AuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase {

    protected suspend fun <T> handleAuthException(call: suspend () -> T): Flow<Resource<T>> {
        return flow {
            emit(Resource.Loading)
            val result = call()
            emit(Resource.Success(result))
        }.catch { throwable ->
            val authException = handleAuthException(throwable)
            emit(Resource.Error(authException.message))
        }
    }

    private fun handleAuthException(throwable: Throwable): AuthException {
        return when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> AuthException.FirebaseAuthInvalidCredentialsException(throwable.errorCode).handleErrorCode()
            is FirebaseAuthInvalidUserException -> AuthException.FirebaseAuthInvalidUserException(throwable.errorCode).handleErrorCode()
            is FirebaseNetworkException -> AuthException.FirebaseNetworkException
            is FirebaseTooManyRequestsException -> AuthException.FirebaseTooManyRequestsException
            is FirebaseAuthUserCollisionException -> AuthException.FirebaseAuthUserCollisionException
            else -> throw throwable
        }
    }
}