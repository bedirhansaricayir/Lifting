package com.lifting.app.core.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.toKg
import com.lifting.app.core.common.extensions.toKgFromLbs
import com.lifting.app.core.common.extensions.toKm
import com.lifting.app.core.common.extensions.toLbs
import com.lifting.app.core.common.extensions.toLbsFromKg
import com.lifting.app.core.common.extensions.toMiles
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.extensions.toLocalizedString

/**
 * Created by bedirhansaricayir on 24.05.2025
 */

/**
 * @param case 0=Pascal Case, 1=Upper Case, 2=Lower Case
 */
@Composable
fun preferencesWeightUnitString(
    case: Int = 2,
    weightUnit: WeightUnit = LocalAppSettings.current.weightUnit,
): String = stringResource(id = weightUnit.toLocalizedString(case = case))

@Composable
fun Double?.toWeightUnitPreferencesString(
    weightUnit: WeightUnit = LocalAppSettings.current.weightUnit,
    addUnitSuffix: Boolean = false,
    spaceBeforeSuffix: Boolean = false
): String {
    val value = when (weightUnit) {
        WeightUnit.Kg -> this.toKg()
        WeightUnit.Lbs -> this.toLbs()
    }

    val space = if (spaceBeforeSuffix) " " else ""

    return buildString {
        append(value)
        append(space)
        if (addUnitSuffix) append(preferencesWeightUnitString(weightUnit = weightUnit))
    }
}

/**
 * @param case 0=Pascal Case, 1=Upper Case, 2=Lower Case
 */
@Composable
fun preferencesDistanceUnitString(
    case: Int = 2,
    distanceUnit: DistanceUnit = LocalAppSettings.current.distanceUnit,
): String = stringResource(id = distanceUnit.toLocalizedString(case = case))

@Composable
fun Double?.toDistanceUnitPreferencesString(
    distanceUnit: DistanceUnit = LocalAppSettings.current.distanceUnit,
    addUnitSuffix: Boolean = false,
    spaceBeforeSuffix: Boolean = false
): String {

    val value = when (distanceUnit) {
        DistanceUnit.Km -> this.toKm()
        DistanceUnit.Miles -> this.toMiles()
    }

    val space = if (spaceBeforeSuffix) " " else ""

    return buildString {
        append(value)
        append(space)
        if (addUnitSuffix) append(preferencesDistanceUnitString(distanceUnit = distanceUnit))
    }
}

@Composable
fun Barbell?.toWeightUnit() = toWeightUnit(LocalAppSettings.current.weightUnit)

fun Barbell?.toWeightUnit(weightUnit: WeightUnit): Double {
    return when (weightUnit) {
        WeightUnit.Lbs -> this?.weightLbs ?: this?.weightKg?.toLbsFromKg() ?: 0.0
        WeightUnit.Kg -> this?.weightKg ?: this?.weightLbs?.toKgFromLbs() ?: 0.0
    }
}