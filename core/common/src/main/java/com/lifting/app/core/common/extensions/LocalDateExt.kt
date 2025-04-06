package com.lifting.app.core.common.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * Created by bedirhansaricayir on 22.03.2025
 */

fun LocalDate.toEpochMillis() =
    this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

fun LocalDateTime.toEpochMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime?.isSameYear() = LocalDate.now().year == this?.year

fun LocalDate.isSameYear() = LocalDate.now().year == this.year
