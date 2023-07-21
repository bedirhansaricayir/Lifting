package com.lifting.app.feature_home.presentation.home

import com.lifting.app.feature_home.data.remote.model.AntrenmanProgramlari
import com.lifting.app.feature_home.data.remote.model.Uygulanis


data class HomePageUiState(
    val isLoading: Boolean? = false,
    val programData: AntrenmanProgramlari? = null,
    val selectedProgramName: String? = null,
    val selectedProgram: ArrayList<Uygulanis> = arrayListOf(),
    val error: String? = null,
    val onPersonalizedProgramCreated: String? = null,
    val userData: UserData? = null
)


data class UserData(
    val userId: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null
)
