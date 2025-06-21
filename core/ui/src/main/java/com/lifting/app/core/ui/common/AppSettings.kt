package com.lifting.app.core.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.WeightUnit

/**
 * Created by bedirhansaricayir on 24.05.2025
 */

data class AppSettings(
    val weightUnit: WeightUnit,
    val distanceUnit: DistanceUnit
) {
    companion object {
        fun defValues(): AppSettings = AppSettings(
            weightUnit = WeightUnit.Kg,
            distanceUnit = DistanceUnit.Km
        )

    }
}

val LocalAppSettings = staticCompositionLocalOf<AppSettings> {
    error("No AppSettings given")
}