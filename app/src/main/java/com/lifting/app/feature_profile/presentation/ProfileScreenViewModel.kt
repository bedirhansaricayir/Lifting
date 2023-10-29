package com.lifting.app.feature_profile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.Resource
import com.lifting.app.common.util.successOr
import com.lifting.app.feature_auth.presentation.google_auth.GoogleAuthUiClient
import com.lifting.app.feature_home.data.local.datastore.DataStoreRepository
import com.lifting.app.feature_profile.domain.use_case.AddImageToStorageUseCase
import com.lifting.app.feature_profile.domain.use_case.AddImageUrlToFirestoreUseCase
import com.lifting.app.feature_profile.domain.use_case.GetProfileSettingsUseCase
import com.lifting.app.feature_home.domain.use_case.GetUserInfoUseCase
import com.lifting.app.feature_profile.domain.use_case.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val addImageToStorageUseCase: AddImageToStorageUseCase,
    private val addImageUrlToFirestoreUseCase: AddImageUrlToFirestoreUseCase,
    private val getProfileSettingsUseCase: GetProfileSettingsUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataStoreRepository: DataStoreRepository,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnProfilePictureSelected -> {
                addImageToStorage(event.uri)
                updateProfilePicture(event.uri)
            }
            is ProfileScreenEvent.OnProfilePictureAddedToStorage -> {
                addImageToFirestore(event.downloadUrl)
            }
            is ProfileScreenEvent.OnLogoutClicked -> {
                signOut()
            }
            is ProfileScreenEvent.OnDeleteAccountClicked -> {
                deleteAccount()
            }
            is ProfileScreenEvent.OnUserViewedTheError -> {
                onUserViewedTheError(false)
            }

        }
    }

    init {
        getProfileSettings()
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke().collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(profileDataLoading = true)
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(
                                email = response.data?.email,
                                username = response.data?.displayName,
                                profilePictureUrl = response.data?.photoUrl,
                                isPremium = response.data?.isPremium,
                                createdAt = response.data?.createdAt
                            )
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(
                                profileDataError = response.e,
                                profileDataLoading = false
                            )
                        )
                    }
                }
            }
        }
    }

    private fun addImageToStorage(imageUri: Uri) {
        viewModelScope.launch {
            addImageToStorageUseCase.invoke(imageUri).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            addImageToStorageLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            addImageToStorageLoading = false,
                            addImageToStorage = response.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            addImageToStorageLoading = false,
                            addImageToStorageError = response.e
                        )
                    }
                }
            }
        }
    }

    private fun addImageToFirestore(downloadUrl: Uri) {
        viewModelScope.launch {
            addImageUrlToFirestoreUseCase.invoke(downloadUrl).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            addImageToFirestoreLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            addImageToFirestoreLoading = false,
                            addImageToFirestore = response.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            addImageToFirestoreLoading = false,
                            addImageToFirestoreError = response.e
                        )
                    }
                }
            }
        }
    }


    private fun getProfileSettings() {
        viewModelScope.launch {
            val settingsDeferred = async { getProfileSettingsUseCase.invoke() }
            val settings = settingsDeferred.await().successOr(emptyList())

            _state.value = _state.value.copy(
                settings = settings
            )
        }
    }

    private fun updateProfilePicture(uri: Uri) {
        _state.value = _state.value.copy(
            profileDataState = ProfileDataState(profilePictureUrl = uri.toString())
        )
    }

    private fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            clearSignInState()
            clearUiState()
        }
    }

    private suspend fun clearSignInState() {
        dataStoreRepository.clearDataStore()
    }

    private fun clearUiState() {
        _state.update {
            ProfileScreenState(profileDataState = ProfileDataState(isPremium = false))
        }
    }

    private fun deleteAccount() {
        setAccountDeleteLoading(true)
        viewModelScope.launch {
            deleteAccountUseCase.invoke().collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(
                                accountDeleteLoading = true
                            )
                        )
                    }
                    is Resource.Success -> {
                        signOut()
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(
                                accountDeleteLoading = true,
                                isPremium = false,
                                isAccountDeleted = true
                            )
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            profileDataState = ProfileDataState(
                                accountDeleteLoading = false,
                                accountDeleteError = true
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setAccountDeleteLoading(state:Boolean) {
        _state.value = _state.value.copy(
            profileDataState = ProfileDataState(
                accountDeleteLoading = state
            )
        )
    }
    private fun onUserViewedTheError(state: Boolean) {
        _state.value = _state.value.copy(
            profileDataState = ProfileDataState(
                accountDeleteError = state
            )
        )
    }
}