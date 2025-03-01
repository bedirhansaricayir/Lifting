package com.lifting.app.core.common.extensions

import java.text.DecimalFormat

/**
 * Created by bedirhansaricayir on 23.02.2025
 */

fun Float?.orEmpty() = this ?: 0.0F
fun Float?.toRpe(): String = this?.orEmpty()?.toFormattedRpe() ?: ""
fun Float.toFormattedRpe(): String = DecimalFormat("#.###").format(this)