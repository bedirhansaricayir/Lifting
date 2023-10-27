package com.lifting.app.feature_calculators.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.successOr
import com.lifting.app.feature_calculators.domain.use_case.GetCalculatorsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorScreenViewModel @Inject constructor(
    private val getCalculatorsDataUseCase: GetCalculatorsDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorScreenState())
    val state: StateFlow<CalculatorScreenState> = _state.asStateFlow()

    init {
        getCalculatorsData()
    }

    private fun getCalculatorsData() {
        viewModelScope.launch {
            val dataDeferred = async { getCalculatorsDataUseCase.invoke() }
            val data = dataDeferred.await().successOr(emptyList())

            _state.value = _state.value.copy(
                data = data
            )
        }
    }
}