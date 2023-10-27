package com.lifting.app.feature_calculators.presentation.tools_detail.one_rep

data class OneRepScreenState(
    val weight: Float = 0f,
    val rep: Int = 1,
    val oneRepMax: List<Double>? = null
)
