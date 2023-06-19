package com.fitness.app.presentation.home

import com.fitness.app.data.remote.Uygulanis

/**
 * Kullanıcı arayüzünde meydana gelebilecek olaylar ve kullanıcı etkileşimlerinin tanımlandığı sınıf
 */
sealed class HomePageEvent {
    data class OnWorkoutProgramPlayButtonClicked(val uygulanis: ArrayList<Uygulanis>,val isim: String) : HomePageEvent()
}
