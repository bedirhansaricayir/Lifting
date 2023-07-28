package com.lifting.app.feature_home.presentation.profile

import android.net.Uri

sealed class ProfileScreenEvent {

    data class OnProfilePictureSelected(val uri: Uri) : ProfileScreenEvent()

    data class OnProfilePictureAddedToStorage(val downloadUrl: Uri) : ProfileScreenEvent()
}