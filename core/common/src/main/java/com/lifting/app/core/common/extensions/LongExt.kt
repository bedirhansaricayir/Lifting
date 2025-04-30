package com.lifting.app.core.common.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

fun Long.toDuration(): String {
    val totalSeconds = this
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return listOfNotNull(
        hours.takeIf { it > 0 }?.let { "${it}h" },
        minutes.takeIf { it > 0 }?.let { "${it}m" },
        seconds.takeIf { it > 0 && hours == 0L && minutes == 0L }?.let { "${it}s" }
    ).joinToString(" ")
}

fun Long.toLocalDate(): LocalDate? = Instant.ofEpochMilli(this)
    .atZone(ZoneId.of("UTC")).toLocalDate()

fun Long.toLocalDateTime(): LocalDateTime? = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

