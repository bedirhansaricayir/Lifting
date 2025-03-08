package com.lifting.app.core.common.extensions

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

fun Long.toDuration(): String {
    val totalSeconds = this / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    val secondsStr = if (seconds > 0 && minutes == 0L && hours == 0L) "${seconds}s" else null
    val minutesStr = if (minutes > 0) "${minutes}m" else null
    val hoursStr = if (hours > 0) "${hours}h" else null
    return listOfNotNull(hoursStr, minutesStr, secondsStr).joinToString(separator = " ")
}