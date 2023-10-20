package com.lifting.app.feature_home.presentation.tools_detail.one_rep

data class OneRepScreenState(
    val lift: Lift = Lift()
)

data class Lift(
    val weight: String = "",
    val rep: String = "1"
)
