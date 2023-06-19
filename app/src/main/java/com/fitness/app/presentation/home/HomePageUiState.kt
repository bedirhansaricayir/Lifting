package com.fitness.app.presentation.home

import com.fitness.app.data.remote.AntrenmanProgramlari
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.Uygulanis
import com.fitness.app.data.remote.YuksekZorluk

/**
 * Uygulama boyunca kullanacağımız dataları bu classta saklıyoruz.
 */
data class HomePageUiState(
    val isLoading: Boolean? = false,
    val programData: AntrenmanProgramlari? = null,
    val dusukZorluk: ArrayList<DusukZorluk?> = arrayListOf(),
    val ortaZorluk: OrtaZorluk? = null,
    val yuksekZorluk: YuksekZorluk? = null,
    val selectedProgramName: String? = null,
    val selectedProgram: ArrayList<Uygulanis> = arrayListOf(),
    val error: String? = null
)
