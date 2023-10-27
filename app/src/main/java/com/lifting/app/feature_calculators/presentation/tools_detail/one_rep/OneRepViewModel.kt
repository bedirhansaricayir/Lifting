package com.lifting.app.feature_calculators.presentation.tools_detail.one_rep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.pow

class OneRepViewModel : ViewModel() {

    private val _state = MutableStateFlow(OneRepScreenState())
    val state: StateFlow<OneRepScreenState> = _state.asStateFlow()

    fun onEvent(event: OneRepScreenEvent) {
        when(event) {
            is OneRepScreenEvent.OnWeightValueChanged -> setWeightValue(event.weight)
            is OneRepScreenEvent.OnRepValueChanged -> setRepValue(event.rep)
        }
    }

    init {
        calculateOneRep()
    }
    private fun calculateOneRep(){
        viewModelScope.launch {
            _state.collect { state ->
                val weight = state.weight
                val rep = state.rep
                val resultList = mutableListOf<Double>()

                var currentPercentage = 100.0
                while (currentPercentage >= 55.0) {
                    val oneRepMax = calculateOneRep(weight, rep)
                    resultList.add(oneRepMax*currentPercentage/100.0)
                    currentPercentage -= 5.0
                }
                _state.value = _state.value.copy(
                    oneRepMax = resultList
                )
            }
        }
    }

    private fun calculateOneRep(weight: Float, rep: Int): Double {
       return 100 * weight / (48.8 + 53.8 * 2.71828.pow(-0.075 * rep))
    }

    private fun setWeightValue(weight: Float) {
        _state.value = _state.value.copy(
            weight = weight
        )
    }

    private fun setRepValue(rep: Int) {
        _state.value = _state.value.copy(
            rep = rep
        )
    }
}