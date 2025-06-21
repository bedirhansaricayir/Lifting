package com.lifting.app.core.common.extensions

import com.lifting.app.core.common.utils.Constants.LBS_IN_KG
import com.lifting.app.core.common.utils.Constants.MILES_IN_KM
import java.text.DecimalFormat

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

private fun Double?.orZero() = this ?: 0.0
fun Double.toReadableString(): String = DecimalFormat("#.###").format(this)
fun Double.toReadableStringWithTwoDecimals(): String = DecimalFormat("#.##").format(this)
private fun Double.toFormattedWeightUnit(): String = DecimalFormat("#.##").format(this)
private fun Double.toFormattedDistanceUnit(): String = DecimalFormat("#.###").format(this)

fun Double?.toKg() = this?.orZero()?.toFormattedWeightUnit().orEmpty()
fun Double.toKgFromLbs() = this / LBS_IN_KG
fun Double?.toKgIfLbs(condition: Boolean) = if (this != null && condition == true) this.toKgFromLbs() else this

fun Double?.toLbs() = this?.orZero()?.toLbsFromKg()?.toFormattedWeightUnit().orEmpty()
fun Double.toLbsFromKg() = this * LBS_IN_KG

fun Double?.toKm(): String = this?.orZero()?.toFormattedDistanceUnit().orEmpty()
private fun Double.toKmFromMiles() = this / MILES_IN_KM
fun Double?.toKmIfMiles(condition: Boolean) = if (this != null && condition == true) this.toKmFromMiles() else this

fun Double?.toMiles(): String = this?.orZero()?.toMilesFromKm()?.toFormattedDistanceUnit().orEmpty()
private fun Double.toMilesFromKm() = this * MILES_IN_KM

