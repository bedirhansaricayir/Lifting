package com.lifting.app.core.common.extensions

import java.text.DecimalFormat

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

fun Double?.orEmpty() = this ?: 0.0
fun Double?.toKg() = this?.orEmpty()?.toFormattedKg() ?: ""
fun Double.toFormattedKg(): String = DecimalFormat("#.##").format(this)
fun Double.toFormattedKm(): String = DecimalFormat("#.###").format(this)


