package com.lifting.app.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toLong(): Long {
   return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneOffset.UTC).toLocalDate()
}

fun LocalDate.toLocaleFormat(pattern: String = "dd MMMM yyyy"): String {
    return this.format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
}