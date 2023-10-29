package com.lifting.app.feature_profile.presentation

import android.net.Uri
import com.lifting.app.feature_profile.domain.model.ProfileSettingsData

data class ProfileScreenState(
    val addImageToStorage:Uri? = null,
    val addImageToStorageLoading: Boolean = false,
    val addImageToStorageError: String? = null,

    val addImageToFirestore: Boolean? = null,
    val addImageToFirestoreLoading: Boolean? = null,
    val addImageToFirestoreError: String? = null,

    val settings: List<ProfileSettingsData> = emptyList(),
    val profileDataState: ProfileDataState = ProfileDataState(profileDataLoading = false)
)

data class ProfileDataState(
    val email: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val isPremium: Boolean? = false,
    val profileDataLoading: Boolean = false,
    val profileDataError: String? =  null,
    val createdAt: String? = null,
    val isAccountDeleted: Boolean = false,
    val accountDeleteLoading: Boolean = false,
    val accountDeleteError: Boolean = false
)