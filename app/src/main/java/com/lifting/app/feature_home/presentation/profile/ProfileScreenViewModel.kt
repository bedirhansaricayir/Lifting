package com.lifting.app.feature_home.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.lifting.app.feature_home.domain.use_case.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {


    fun onEvent(event: ProfileScreenEvent) {
        when(event) {
            is ProfileScreenEvent.OnProfilePictureSelected -> {
                profilePicture(event.uri)
            }
        }
    }

    private fun profilePicture(uri: Uri) {
        Log.d("uri",uri.toString())
    }
}