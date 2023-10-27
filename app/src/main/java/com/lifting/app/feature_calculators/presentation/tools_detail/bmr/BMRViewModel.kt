package com.lifting.app.feature_calculators.presentation.tools_detail.bmr

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BMRViewModel : ViewModel() {

    private val _state = MutableStateFlow(BMRScreenState())
    val state: StateFlow<BMRScreenState> = _state.asStateFlow()

    fun onEvent(event: BMRScreenEvent) {
        when(event) {
            is BMRScreenEvent.OnGenderSelected -> onGenderSelected(event.gender)
            is BMRScreenEvent.OnActivityLevelSelected -> onActivityLevelSelected(event.activityLevel)
            is BMRScreenEvent.OnCalculateButtonClicked -> calculateBMR(event.measurements)
        }
    }

    private fun onGenderSelected(gender: Gender) {
        _state.value = _state.value.copy(
            gender = gender
        )
    }

    private fun onActivityLevelSelected(activityLevel: ActivityLevel) {
        _state.value = _state.value.copy(
            activityLevel = activityLevel
        )
    }

    private fun calculateBMR(measurements: BodyMeasurements) {
        val bmrValue: Double = when(_state.value.gender) {
            Gender.MEN -> (10 * measurements.weight.toDouble()) + (6.25 * measurements.height.toDouble()) - (5 * measurements.age) + 5
            Gender.WOMEN -> (10 * measurements.weight.toDouble()) + (6.25 * measurements.height.toDouble()) - (5 * measurements.age) - 161
        }

        val dailyCalories: Double = when(_state.value.activityLevel) {
            ActivityLevel.SEDENTARY -> bmrValue.times(1.2)
            ActivityLevel.LOW -> bmrValue.times(1.375)
            ActivityLevel.MEDIUM -> bmrValue.times(1.55)
            ActivityLevel.HIGH -> bmrValue.times(1.725)
        }

        _state.value = _state.value.copy(
            bmr = bmrValue,
            dailyCalories = dailyCalories
        )
    }
}