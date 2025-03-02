package com.lifting.app.core.common.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

fun LocalDateTime.toReadableFormat(): String =
    this.format(
        DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM,
            FormatStyle.SHORT
        )
    )
