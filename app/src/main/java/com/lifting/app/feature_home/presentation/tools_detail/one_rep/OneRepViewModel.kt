package com.lifting.app.feature_home.presentation.tools_detail.one_rep

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OneRepViewModel : ViewModel() {

    private val _state = MutableStateFlow(OneRepScreenState())
    val state: StateFlow<OneRepScreenState> = _state.asStateFlow()

    fun onEvent(event: OneRepScreenEvent) {
        when(event) {
            is OneRepScreenEvent.OnWeightValueChanged -> setWeightValue(event.weight)
        }
    }

    private fun setWeightValue(weight: String) {
        _state.value = _state.value.copy(
            lift = Lift(weight = weight)
        )
    }
}