package com.lifting.app.feature_auth.presentation

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.local.datastore.DataStoreRepository
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.domain.model.PasswordRequirements
import com.lifting.app.feature_auth.domain.model.InputValidationType
import com.lifting.app.feature_auth.domain.use_case.AddUserToFirestoreUseCase
import com.lifting.app.feature_auth.domain.use_case.AuthenticationUseCase
import com.lifting.app.feature_auth.domain.use_case.EmailInputValidationUseCase
import com.lifting.app.feature_auth.domain.use_case.ReloadUserUseCase
import com.lifting.app.feature_auth.domain.use_case.SendEmailVerificationUseCase
import com.lifting.app.feature_auth.domain.use_case.SendPasswordResetUseCase
import com.lifting.app.feature_auth.domain.use_case.SignInUseCase
import com.lifting.app.feature_auth.domain.use_case.SignUpUseCase
import com.lifting.app.feature_auth.presentation.forgot_password.ForgotPasswordState
import com.lifting.app.feature_auth.presentation.google_auth.GoogleAuthUiClient
import com.lifting.app.feature_auth.presentation.google_auth.GoogleSignInState
import com.lifting.app.feature_auth.presentation.google_auth.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val emailInputValidationUseCase: EmailInputValidationUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataStoreRepository: DataStoreRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val reloadUserUseCase: ReloadUserUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val addUserToFirestoreUseCase: AddUserToFirestoreUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthenticationState())
    val authState: StateFlow<AuthenticationState> = _authState.asStateFlow()

    private val _googleState = MutableStateFlow(GoogleSignInState())
    val googleState: StateFlow<GoogleSignInState> = _googleState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState.asStateFlow()

    fun onEvent(authenticationEvent: AuthenticationEvent) {
        when (authenticationEvent) {
            is AuthenticationEvent.ToggleAuthenticationMode -> {
                toggleAuthenticationMode()
            }

            is AuthenticationEvent.UsernameChanged -> {
                updateUsername(authenticationEvent.username)
            }

            is AuthenticationEvent.EmailChanged -> {
                updateEmail(authenticationEvent.emailAddress)
                checkInputValidation(authenticationEvent.emailAddress)
            }

            is AuthenticationEvent.PasswordChanged -> {
                updatePassword(authenticationEvent.password)
            }

            is AuthenticationEvent.SignUpButtonClicked -> {
                emailPasswordSignUp(
                    authenticationEvent.username,
                    authenticationEvent.email,
                    authenticationEvent.password
                )
            }

            is AuthenticationEvent.OnVerificationRequired -> {
                sendEmailVerification()
            }

            is AuthenticationEvent.ErrorDismissed -> {
                dismissError()
            }

            is AuthenticationEvent.ToggleVisualTransformation -> {
                toggleVisualTransformation()
            }

            is AuthenticationEvent.SignInButtonClicked -> {
                emailPasswordSignIn(authenticationEvent.email, authenticationEvent.password)
            }

            is AuthenticationEvent.OnSignInResultGoogle -> {
                onSignInResult(authenticationEvent.signInResult)
            }

            is AuthenticationEvent.OnGoogleButtonDisabled -> {
                onGoogleButtonDisabled()
            }

            is AuthenticationEvent.OnGoogleButtonEnabled -> {
                onGoogleButtonEnabled()
            }

            is AuthenticationEvent.ReloadFirebaseUser -> {
                reloadFirebaseUser()
            }

            is AuthenticationEvent.AddUserToFirestore -> {
                addUserToFirestore()
            }

            is AuthenticationEvent.OnSignInSuccessful -> {
                saveSuccessfullySignInState(authenticationEvent.isSuccessful)
            }

            is AuthenticationEvent.ClearForgotPasswordState -> {
                clearForgotPasswordState()
            }

            is AuthenticationEvent.ForgotPasswordEmailChanged -> {
                updateForgotPasswordEmail(authenticationEvent.emailAddress)
                checkInputValidation(authenticationEvent.emailAddress)
            }

            is AuthenticationEvent.ForgotPasswordErrorDismissed -> {
                dismissForgotPasswordError()
            }

            is AuthenticationEvent.OnResetPasswordRequest -> {
                sendPasswordResetEmail(authenticationEvent.email)
            }
        }
    }

    private fun toggleAuthenticationMode() {
        val authenticationMode = _authState.value.authenticationMode
        val newAuthenticationMode = if (
            authenticationMode == AuthenticationMode.SIGN_IN
        ) {
            AuthenticationMode.SIGN_UP
        } else {
            AuthenticationMode.SIGN_IN
        }
        _authState.value = _authState.value.copy(
            authenticationMode = newAuthenticationMode
        )
    }

    private fun updateUsername(username: String) {
        _authState.value = _authState.value.copy(
            username = username
        )
    }

    private fun updateEmail(email: String) {
        _authState.value = _authState.value.copy(
            email = email
        )
    }

    private fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirements>()
        if (password.length > 7) {
            requirements.add(PasswordRequirements.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirements.CAPITAL_LETTER)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirements.NUMBER)
        }
        _authState.value = _authState.value.copy(
            password = password,
            passwordRequirements = requirements.toList()
        )
    }


    private fun emailPasswordSignIn(email: String, password: String) {
        _authState.value = _authState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            signInUseCase.invoke(email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _authState.value = _authState.value.copy(
                            authResult = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _authState.value = _authState.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            error = result.e
                        )
                    }
                }
            }
        }
    }

    private fun emailPasswordSignUp(username: String, email: String, password: String) {
        _authState.value = _authState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            signUpUseCase.invoke(username, email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _authState.value = _authState.value.copy(
                            authResult = result.data,
                            isLoading = false
                        )
                        sendEmailVerification()
                    }

                    is Resource.Loading -> {
                        _authState.value = _authState.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            error = result.e
                        )
                    }
                }
            }
        }
    }

    private fun sendEmailVerification() {
        viewModelScope.launch {
            sendEmailVerificationUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _authState.value = _authState.value.copy(
                            sendEmailVerification = result.data!!,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _authState.value = _authState.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            error = result.e
                        )
                    }
                }
            }
        }

    }

    private fun reloadFirebaseUser() = viewModelScope.launch {
        reloadUserUseCase.invoke().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _authState.value = _authState.value.copy(
                        isUserReloaded = result.data!!,
                        isEmailVerified = authenticationUseCase.provideCurrentUser()?.isEmailVerified ?: false
                    )
                }

                is Resource.Loading -> {
                    _authState.value = _authState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = result.e
                    )
                }
            }
        }
    }

    private fun addUserToFirestore() = viewModelScope.launch {
        addUserToFirestoreUseCase.invoke()
    }


    suspend fun signInWithIntent(intent: Intent): SignInResult =
        googleAuthUiClient.signInWithIntent(intent, isNewUser = { addUserToFirestore() })

    suspend fun signIn() = googleAuthUiClient.signIn()
    private fun onSignInResult(result: SignInResult) {
        _googleState.value = _googleState.value.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
    }

    private fun onGoogleButtonDisabled() {
        _googleState.value = _googleState.value.copy(
            googleButtonClickableState = false
        )
    }

    private fun onGoogleButtonEnabled() {
        _googleState.value = _googleState.value.copy(
            googleButtonClickableState = true
        )
    }


    private fun signOut() = authenticationUseCase.signOut()


    private fun dismissError() {
        _authState.value = _authState.value.copy(
            error = null
        )
    }


    private fun toggleVisualTransformation() {
        _authState.value = _authState.value.copy(
            isPasswordShown = !_authState.value.isPasswordShown
        )
    }

    private fun clearForgotPasswordState() {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(
            email = null,
            error = null,
            isEmailValid = true,
            isLoading = false,
            isSend = false
        )
    }

    private fun updateForgotPasswordEmail(email: String) {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(
            email = email
        )
    }

    private fun dismissForgotPasswordError() {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(
            error = null
        )
    }

    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        sendPasswordResetUseCase.invoke(email).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _forgotPasswordState.value = _forgotPasswordState.value.copy(
                        isSend = result.data!!,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    _forgotPasswordState.value = _forgotPasswordState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _forgotPasswordState.value = _forgotPasswordState.value.copy(
                        error = result.e,
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun checkInputValidation(email: String) {
        val result = emailInputValidationUseCase.invoke(email)
        processInputValidation(result)
    }

    private fun processInputValidation(type: InputValidationType) {
        when (type) {
            InputValidationType.NoEmail -> {
                _authState.value = _authState.value.copy(
                    emailError = true
                )
                _forgotPasswordState.value = _forgotPasswordState.value.copy(
                    isEmailValid = false
                )
            }

            InputValidationType.Valid -> {
                _authState.value = _authState.value.copy(
                    emailError = false
                )
                _forgotPasswordState.value = _forgotPasswordState.value.copy(
                    isEmailValid = true
                )
            }
        }
    }

    private fun saveSuccessfullySignInState(completed: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSuccessfullySignInState(completed)
        }
}
