package com.lifting.app.feature_calculators.presentation

import com.lifting.app.feature_calculators.domain.model.GridItemData

data class CalculatorScreenState(
    val data: List<GridItemData> = emptyList()
)
