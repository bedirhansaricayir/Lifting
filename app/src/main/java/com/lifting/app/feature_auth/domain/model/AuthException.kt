package com.lifting.app.feature_auth.domain.model

import com.lifting.app.feature_auth.domain.model.AuthExceptionConstants.Companion.ERROR_INVALID_EMAIL
import com.lifting.app.feature_auth.domain.model.AuthExceptionConstants.Companion.ERROR_USER_DISABLED
import com.lifting.app.feature_auth.domain.model.AuthExceptionConstants.Companion.ERROR_WRONG_PASSWORD

sealed class AuthException(message: String) : Exception(message) {
    class FirebaseAuthInvalidCredentialsException(private val errorCode: String) :
        AuthException("The password is invalid or the user does not have a password") {
        fun handleErrorCode(): AuthException {
            return when (errorCode) {
                ERROR_INVALID_EMAIL -> InvalidEmailException
                ERROR_WRONG_PASSWORD -> InvalidPasswordException

                else -> InvalidCredentialsExceptionsThatMayOccurInOtherCases
            }
        }

        object InvalidEmailException : AuthException("Enter a valid email")
        object InvalidPasswordException : AuthException("The password is invalid")
        object InvalidCredentialsExceptionsThatMayOccurInOtherCases :
            AuthException("The password is invalid or the user does not have a password")

    }

    class FirebaseAuthInvalidUserException(private val errorCode: String) :
        AuthException("User information not found") {
        fun handleErrorCode(): AuthException {
            return when (errorCode) {
                ERROR_USER_DISABLED -> DisabledUserException
                else -> InvalidUserExceptionsThatMayOccurInOtherCases
            }
        }

        object DisabledUserException : AuthException("Your account has been disabled")
        object InvalidUserExceptionsThatMayOccurInOtherCases :
            AuthException("User information not found")


    }

    object FirebaseNetworkException : AuthException("Please check your connection or try again")
    object FirebaseTooManyRequestsException :
        AuthException("You have been blocked for too many attempts. Try again later")

    object FirebaseAuthUserCollisionException : AuthException("Email is already in use")


}


class AuthExceptionConstants {
    companion object {
        const val ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL"
        const val ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
        const val ERROR_USER_DISABLED = "ERROR_USER_DISABLED"
    }
}