package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 24.05.2025
 */

enum class DistanceUnit {
    Km, Miles
}

fun DistanceUnit.isMiles() = this == DistanceUnit.Miles