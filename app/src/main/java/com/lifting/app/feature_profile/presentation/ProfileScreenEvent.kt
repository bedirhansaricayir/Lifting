package com.lifting.app.feature_profile.presentation

import android.net.Uri

sealed class ProfileScreenEvent {

    data class OnProfilePictureSelected(val uri: Uri) : ProfileScreenEvent()

    data class OnProfilePictureAddedToStorage(val downloadUrl: Uri) : ProfileScreenEvent()

    object OnLogoutClicked : ProfileScreenEvent()

    object OnDeleteAccountClicked : ProfileScreenEvent()

    object OnUserViewedTheError : ProfileScreenEvent()

}