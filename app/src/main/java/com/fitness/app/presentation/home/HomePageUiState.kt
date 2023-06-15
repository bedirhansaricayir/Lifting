package com.fitness.app.presentation.home

import com.fitness.app.data.remote.AntrenmanProgramlari

data class HomePageUiState(
    val isLoading: Boolean? = false,
    val programData: AntrenmanProgramlari? = null,
    val error: String? = null
)
