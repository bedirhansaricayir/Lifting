package com.lifting.app.common.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Formatter
import java.util.Locale

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick.invoke()
    }
}

fun Long.toTimeFormat(): String {
    val mFormatBuilder = StringBuilder()
    val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
    val totalSeconds = this / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    mFormatBuilder.setLength(0)
    return if (hours > 0) {
        mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
    } else {
        mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }
}

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