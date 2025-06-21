package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 31.05.2025
 */

enum class TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT;

    companion object {
        fun now() = fromDateTime(LocalDateTime.now())

        fun fromDateTime(localDateTime: LocalDateTime): TimePeriod {
            val hours = localDateTime.hour
            return when {
                hours <= 12 -> MORNING

                hours <= 16 -> AFTERNOON

                hours <= 21 -> EVENING

                else -> NIGHT
            }
        }
    }
}