package com.lifting.app.feature_auth.data.repository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.lifting.app.core.util.Resource
import com.lifting.app.feature_auth.domain.model.AuthException
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun emailAndPasswordSignIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch { throwable ->
            val signInException = handleSignInException(throwable)
            Log.d("signInException",throwable.toString())
            emit(Resource.Error(signInException.message))
        }
    }

    fun handleSignInException(throwable: Throwable) : AuthException {
        return when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> AuthException.FirebaseAuthInvalidCredentialsException(throwable.errorCode).handleErrorCode()
            is FirebaseAuthInvalidUserException -> AuthException.FirebaseAuthInvalidUserException(throwable.errorCode).handleErrorCode()
            is FirebaseNetworkException -> AuthException.FirebaseNetworkException
            is FirebaseTooManyRequestsException -> AuthException.FirebaseTooManyRequestsException
            is FirebaseAuthUserCollisionException -> AuthException.FirebaseAuthUserCollisionException
            else -> throw throwable
        }
    }



    override fun emailAndPasswordSignUp(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(username).build()
            )?.await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun sendEmailVerification(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun reloadFirebaseUser(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            firebaseAuth.currentUser?.reload()?.await()
            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun sendPasswordResetEmail(email: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun signOut() = firebaseAuth.signOut()

}