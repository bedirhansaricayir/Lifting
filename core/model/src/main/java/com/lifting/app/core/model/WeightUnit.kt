package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 24.05.2025
 */

enum class WeightUnit {
    Kg, Lbs
}

fun WeightUnit.isLbs() = this == WeightUnit.Lbs