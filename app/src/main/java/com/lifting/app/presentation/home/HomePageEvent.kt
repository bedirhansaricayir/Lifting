package com.lifting.app.presentation.home

import com.lifting.app.data.remote.model.Uygulanis


sealed class HomePageEvent {
    data class OnWorkoutProgramPlayButtonClicked(val uygulanis: ArrayList<Uygulanis>, val isim: String) : HomePageEvent()
    data class OnPersonalizedProgramButtonClicked(val days: String, val goal: String) : HomePageEvent()
}
