package com.lifting.app.core.common.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

fun Long.toDuration(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return listOfNotNull(
        hours.takeIf { it > 0 }?.let { "${it}h" },
        minutes.takeIf { it > 0 }?.let { "${it}m" },
        seconds.takeIf { it > 0 && hours == 0L && minutes == 0L }?.let { "${it}s" }
    ).joinToString(" ")
}

fun Long.toDuration2(): String {
    val duration = this.toDuration(DurationUnit.MILLISECONDS)

    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60

    return listOfNotNull(
        hours.takeIf { it > 0 }?.let { "${it}h" },
        minutes.takeIf { it > 0 }?.let { "${it}m" },
        seconds.takeIf { it > 0 && hours == 0L && minutes == 0L }?.let { "${it}s" }
    ).joinToString(" ")
}

fun Long.toDurationHms(): Triple<Long, Long, Long> {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return Triple(hours, minutes, seconds)
}

fun Long.toLocalDate(): LocalDate? = Instant.ofEpochMilli(this)
    .atZone(ZoneId.systemDefault()).toLocalDate()

fun Long.toLocalDateTime(): LocalDateTime? =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

fun Long.toHmsFormat(
    endAt: Long = LocalDateTime.now().toEpochMillis(),
    useSpaces: Boolean = true,
    hoursAndMinutesOnly: Boolean = false,
): String {
    val durationMillis = endAt - this
    val totalSeconds = durationMillis / 1000

    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    val separator = if (useSpaces) " : " else ":"

    val hoursPart = if (hoursAndMinutesOnly || hours > 0) String.format(locale = Locale.getDefault(),"%02d", hours) + separator else ""
    val minutesPart = String.format(locale = Locale.getDefault(),"%02d", minutes)
    val secondsPart = if (hoursAndMinutesOnly) "" else separator + String.format(locale = Locale.getDefault(),"%02d", seconds)

    return hoursPart + minutesPart + secondsPart
}

fun Long.toMMSSFromString(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(locale = Locale.getDefault(),"%02d:%02d", minutes, seconds)
}