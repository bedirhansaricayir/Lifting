package com.lifting.app.feature_home.presentation.tools_detail.bmr

sealed class BMRScreenEvent {
    data class OnGenderSelected(val gender: Gender) : BMRScreenEvent()
    data class OnActivityLevelSelected(val activityLevel: ActivityLevel) : BMRScreenEvent()
    data class OnCalculateButtonClicked(val measurements: BodyMeasurements) : BMRScreenEvent()
}
