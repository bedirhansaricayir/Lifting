package com.lifting.app.feature_home.presentation.tools_detail.one_rep

sealed class OneRepScreenEvent {
    data class OnWeightValueChanged(val weight: String) : OneRepScreenEvent()

}

