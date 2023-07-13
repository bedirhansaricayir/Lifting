package com.lifting.app.presentation.home

import com.lifting.app.data.remote.model.AntrenmanProgramlari
import com.lifting.app.data.remote.model.Uygulanis


data class HomePageUiState(
    val isLoading: Boolean? = false,
    val programData: AntrenmanProgramlari? = null,
    val selectedProgramName: String? = null,
    val selectedProgram: ArrayList<Uygulanis> = arrayListOf(),
    val error: String? = null,
    val onPersonalizedProgramCreated: String? = null
)