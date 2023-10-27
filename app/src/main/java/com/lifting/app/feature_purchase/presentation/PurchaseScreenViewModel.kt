package com.lifting.app.feature_purchase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.util.successOr
import com.lifting.app.feature_purchase.domain.model.PurchaseScreenData
import com.lifting.app.feature_purchase.domain.use_case.GetProductCardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseScreenViewModel @Inject constructor(
    private val getProductCardDataUseCase: GetProductCardDataUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PurchaseScreenState())
    val state: StateFlow<PurchaseScreenState> = _state.asStateFlow()

    init {
        getProductCardData()
    }

    private fun getProductCardData() {
        viewModelScope.launch {
            val dataDeferred =  async { getProductCardDataUseCase.invoke() }
            val data = dataDeferred.await().successOr(PurchaseScreenData(emptyList(), emptyList()))
            _state.value = _state.value.copy(
                productCardData = data.productCardDataList,
                providedFeaturesData = data.providedFeaturesList
            )
        }
    }
}