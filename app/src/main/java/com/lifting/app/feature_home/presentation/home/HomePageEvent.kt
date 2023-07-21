package com.lifting.app.feature_home.presentation.home

import com.lifting.app.feature_home.data.remote.model.Uygulanis


sealed class HomePageEvent {
    data class OnWorkoutProgramPlayButtonClicked(val uygulanis: ArrayList<Uygulanis>, val isim: String) : HomePageEvent()
    data class OnPersonalizedProgramButtonClicked(val days: String, val goal: String) : HomePageEvent()
}
