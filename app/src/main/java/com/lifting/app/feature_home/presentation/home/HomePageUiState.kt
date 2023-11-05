package com.lifting.app.feature_home.presentation.home

import com.lifting.app.feature_home.data.remote.model.AntrenmanProgramlari
import com.lifting.app.feature_home.data.remote.model.Uygulanis


data class HomePageUiState(
    val isLoading: Boolean = false,
    val programData: AntrenmanProgramlari? = null,
    val error: String? = null,
    val onPersonalizedProgramCreated: String? = null,
)
data class UserDataState(
    val email: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val isPremium: Boolean? = false,
    val userDataLoading: Boolean = false,
    val userDataError: String? = null
)
