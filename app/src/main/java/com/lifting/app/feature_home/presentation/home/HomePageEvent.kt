package com.lifting.app.feature_home.presentation.home



sealed class HomePageEvent {
    data class OnPersonalizedProgramButtonClicked(val days: String, val goal: String) : HomePageEvent()
}
