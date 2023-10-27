package com.lifting.app.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
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

fun String.toLocalDate(pattern: String = "dd MMMM yyyy"): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern(pattern,Locale.getDefault())
    return try {
        LocalDate.parse(this,formatter)
    } catch (e: Exception) {
        null
    }
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.getDefault())
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}