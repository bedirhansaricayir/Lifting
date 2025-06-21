package com.lifting.app.core.common.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by bedirhansaricayir on 22.03.2025
 */

fun LocalDate.toEpochMillis() =
    this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime.toEpochMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime?.isSameYear() = LocalDate.now().year == this?.year

fun LocalDate.isSameYear() = LocalDate.now().year == this.year

fun LocalDateTime.toReadableDuration(
    endAt: LocalDateTime = LocalDateTime.now(),
    useSpaces: Boolean = true,
    hoursAndMinutesOnly: Boolean = false,
): String {
    return this.toEpochMillis()
        .toHmsFormat(
            endAt = endAt.toEpochMillis(),
            useSpaces = useSpaces,
            hoursAndMinutesOnly = hoursAndMinutesOnly
        )
}
