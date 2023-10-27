package com.lifting.app.feature_calculators.presentation.tools_detail.one_rep

sealed class OneRepScreenEvent {
    data class OnWeightValueChanged(val weight: Float) : OneRepScreenEvent()
    data class OnRepValueChanged(val rep: Int) : OneRepScreenEvent()

}

