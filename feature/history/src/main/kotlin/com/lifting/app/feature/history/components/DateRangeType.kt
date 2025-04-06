package com.lifting.app.feature.history.components

/**
 * Created by bedirhansaricayir on 22.03.2025
 */

sealed interface WorkoutsDateRangeType {
    data object All : WorkoutsDateRangeType
    data class Month(val month: Int, val year: Int) : WorkoutsDateRangeType
    data class Year(val year: Int) : WorkoutsDateRangeType
    data class Day(val day: Int, val month: Int, val year: Int) : WorkoutsDateRangeType
}