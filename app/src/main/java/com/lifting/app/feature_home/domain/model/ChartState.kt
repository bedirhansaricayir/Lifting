package com.lifting.app.feature_home.domain.model

import java.time.LocalDate

data class ChartState(
    val dateWithoutTime: LocalDate,
    val bodyweight: Float?,
    val cj: Float?,
    val snatch: Float?
)
