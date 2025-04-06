package com.lifting.app.feature.calendar.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lifting.app.feature.calendar.models.CalendarMonth
import com.lifting.app.feature.calendar.models.InDateStyle
import com.lifting.app.feature.calendar.models.MonthConfig
import com.lifting.app.feature.calendar.models.OutDateStyle
import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

class CalendarPagingDataSource(
    private val startYear: Int,
    private val firstDayOfWeek: DayOfWeek,
) :
    PagingSource<Int, CalendarMonth>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CalendarMonth> {
        val year = params.key ?: startYear
        return try {
            val monthConfig = MonthConfig(
                outDateStyle = OutDateStyle.NONE,
                inDateStyle = InDateStyle.ALL_MONTHS,
                startMonth = YearMonth.of(year, Month.JANUARY),
                endMonth = YearMonth.of(year, Month.DECEMBER),
                hasBoundaries = true,
                maxRowCount = Int.MAX_VALUE,
                firstDayOfWeek = firstDayOfWeek,
                job = Job()
            )


            LoadResult.Page(
                data = monthConfig.months,
                prevKey = year - 1,
                nextKey = year + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CalendarMonth>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}