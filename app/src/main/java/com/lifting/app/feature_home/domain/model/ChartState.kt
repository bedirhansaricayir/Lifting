package com.lifting.app.feature_home.domain.model

import java.time.LocalDate

data class ChartState(
    val date: LocalDate,
    val data: Float,
    val description: String
)
