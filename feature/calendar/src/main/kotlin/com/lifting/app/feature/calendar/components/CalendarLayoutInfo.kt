package com.lifting.app.feature.calendar.components

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo

/**
 * Created by bedirhansaricayir on 06.06.2025
 */

/**
 * Contains useful information about the currently displayed layout state of the calendar.
 * For example you can get the list of currently displayed months.
 *
 * Use [CalendarState.layoutInfo] to retrieve this.
 * @see LazyListLayoutInfo
 */
class CalendarLayoutInfo(info: LazyListLayoutInfo, private val month: (Int) -> CalendarMonth) :
    LazyListLayoutInfo by info {
    /**
     * The list of [CalendarItemInfo] representing all the currently visible months.
     */
    val visibleMonthsInfo: List<CalendarItemInfo>
        get() = visibleItemsInfo.map {
            CalendarItemInfo(it, month(it.index))
        }
}

/**
 * Contains useful information about an individual [CalendarMonth] on the calendar.
 *
 * @param month The month in the list.
 *
 * @see CalendarLayoutInfo
 * @see LazyListItemInfo
 */
class CalendarItemInfo(info: LazyListItemInfo, val month: CalendarMonth) : LazyListItemInfo by info