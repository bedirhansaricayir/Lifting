package com.lifting.app.feature.calendar.models

import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields

internal data class MonthConfig(
    internal val outDateStyle: OutDateStyle,
    internal val inDateStyle: InDateStyle,
    internal val maxRowCount: Int,
    internal val startMonth: YearMonth,
    internal val endMonth: YearMonth,
    internal val firstDayOfWeek: DayOfWeek,
    internal val hasBoundaries: Boolean,
    internal val job: Job
) {

    internal val months: List<CalendarMonth> = run {
        return@run if (hasBoundaries) {
            generateBoundedMonths(
                startMonth,
                endMonth,
                firstDayOfWeek,
                maxRowCount,
                inDateStyle,
                outDateStyle,
                job
            )
        } else {
            generateUnboundedMonths(
                startMonth,
                endMonth,
                firstDayOfWeek,
                maxRowCount,
                inDateStyle,
                outDateStyle,
                job
            )
        }
    }

    internal companion object {

        private val uninterruptedJob = Job()

        fun generateBoundedMonths(
            startMonth: YearMonth,
            endMonth: YearMonth,
            firstDayOfWeek: DayOfWeek,
            maxRowCount: Int,
            inDateStyle: InDateStyle,
            outDateStyle: OutDateStyle,
            job: Job = uninterruptedJob
        ): List<CalendarMonth> {
            val months = mutableListOf<CalendarMonth>()
            var currentMonth = startMonth
            while (currentMonth <= endMonth && job.isActive) {
                val generateInDates = when (inDateStyle) {
                    InDateStyle.ALL_MONTHS -> true
                    InDateStyle.FIRST_MONTH -> currentMonth == startMonth
                    InDateStyle.NONE -> false
                }

                val weekDaysGroup =
                    generateWeekDays(currentMonth, firstDayOfWeek, generateInDates, outDateStyle)

                val calendarMonths = mutableListOf<CalendarMonth>()
                val numberOfSameMonth = weekDaysGroup.size roundDiv maxRowCount
                var indexInSameMonth = 0
                calendarMonths.addAll(weekDaysGroup.chunked(maxRowCount) { monthDays ->
                    CalendarMonth(
                        currentMonth,
                        monthDays.toList(),
                        indexInSameMonth++,
                        numberOfSameMonth
                    )
                })

                months.addAll(calendarMonths)
                if (currentMonth != endMonth) currentMonth = currentMonth.next else break
            }

            return months
        }

        internal fun generateUnboundedMonths(
            startMonth: YearMonth,
            endMonth: YearMonth,
            firstDayOfWeek: DayOfWeek,
            maxRowCount: Int,
            inDateStyle: InDateStyle,
            outDateStyle: OutDateStyle,
            job: Job = uninterruptedJob
        ): List<CalendarMonth> {

            val allDays = mutableListOf<CalendarDay>()
            var currentMonth = startMonth
            while (currentMonth <= endMonth && job.isActive) {
                val generateInDates = when (inDateStyle) {
                    InDateStyle.FIRST_MONTH, InDateStyle.ALL_MONTHS -> currentMonth == startMonth
                    InDateStyle.NONE -> false
                }

                allDays.addAll(
                    generateWeekDays(
                        currentMonth,
                        firstDayOfWeek,
                        generateInDates,
                        OutDateStyle.NONE
                    ).flatten()
                )
                if (currentMonth != endMonth) currentMonth = currentMonth.next else break
            }

            val allDaysGroup = allDays.chunked(7).toList()

            val calendarMonths = mutableListOf<CalendarMonth>()
            val calMonthsCount = allDaysGroup.size roundDiv maxRowCount
            allDaysGroup.chunked(maxRowCount) { ephemeralMonthWeeks ->
                val monthWeeks = ephemeralMonthWeeks.toMutableList()

                if (monthWeeks.last().size < 7 && outDateStyle == OutDateStyle.END_OF_ROW || outDateStyle == OutDateStyle.END_OF_GRID) {
                    val lastWeek = monthWeeks.last()
                    val lastDay = lastWeek.last()
                    val outDates = (1..7 - lastWeek.size).map {
                        CalendarDay(lastDay.date.plusDays(it.toLong()), DayOwner.NEXT_MONTH)
                    }
                    monthWeeks[monthWeeks.lastIndex] = lastWeek + outDates
                }

                while (monthWeeks.size < maxRowCount && outDateStyle == OutDateStyle.END_OF_GRID ||
                    monthWeeks.size == maxRowCount && monthWeeks.last().size < 7 && outDateStyle == OutDateStyle.END_OF_GRID
                ) {

                    val lastDay = monthWeeks.last().last()

                    val nextRowDates = (1..7).map {
                        CalendarDay(lastDay.date.plusDays(it.toLong()), DayOwner.NEXT_MONTH)
                    }

                    if (monthWeeks.last().size < 7) {
                        monthWeeks[monthWeeks.lastIndex] =
                            (monthWeeks.last() + nextRowDates).take(7)
                    } else {
                        monthWeeks.add(nextRowDates)
                    }
                }

                calendarMonths.add(
                    CalendarMonth(startMonth, monthWeeks, calendarMonths.size, calMonthsCount)
                )
            }

            return calendarMonths
        }


        private fun generateWeekDays(
            yearMonth: YearMonth,
            firstDayOfWeek: DayOfWeek,
            generateInDates: Boolean,
            outDateStyle: OutDateStyle
        ): List<List<CalendarDay>> {
            val year = yearMonth.year
            val month = yearMonth.monthValue

            val thisMonthDays = (1..yearMonth.lengthOfMonth()).map {
                CalendarDay(LocalDate.of(year, month, it), DayOwner.THIS_MONTH)
            }

            val weekDaysGroup = if (generateInDates) {
                val weekOfMonthField = WeekFields.of(firstDayOfWeek, 1).weekOfMonth()
                val groupByWeekOfMonth =
                    thisMonthDays.groupBy { it.date.get(weekOfMonthField) }.values.toMutableList()

                val firstWeek = groupByWeekOfMonth.first()
                if (firstWeek.size < 7) {
                    val previousMonth = yearMonth.minusMonths(1)
                    val inDates = (1..previousMonth.lengthOfMonth()).toList()
                        .takeLast(7 - firstWeek.size).map {
                            CalendarDay(
                                LocalDate.of(previousMonth.year, previousMonth.month, it),
                                DayOwner.PREVIOUS_MONTH
                            )
                        }
                    groupByWeekOfMonth[0] = inDates + firstWeek
                }
                groupByWeekOfMonth
            } else {
                thisMonthDays.chunked(7).toMutableList()
            }

            if (outDateStyle == OutDateStyle.END_OF_ROW || outDateStyle == OutDateStyle.END_OF_GRID) {
                if (weekDaysGroup.last().size < 7) {
                    val lastWeek = weekDaysGroup.last()
                    val lastDay = lastWeek.last()
                    val outDates = (1..7 - lastWeek.size).map {
                        CalendarDay(lastDay.date.plusDays(it.toLong()), DayOwner.NEXT_MONTH)
                    }
                    weekDaysGroup[weekDaysGroup.lastIndex] = lastWeek + outDates
                }

                if (outDateStyle == OutDateStyle.END_OF_GRID) {
                    while (weekDaysGroup.size < 6) {
                        val lastDay = weekDaysGroup.last().last()
                        val nextRowDates = (1..7).map {
                            CalendarDay(lastDay.date.plusDays(it.toLong()), DayOwner.NEXT_MONTH)
                        }
                        weekDaysGroup.add(nextRowDates)
                    }
                }
            }

            return weekDaysGroup
        }
    }
}

private infix fun Int.roundDiv(other: Int): Int {
    val div = this / other
    val rem = this % other
    return if (rem == 0) div else div + 1
}

internal val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

internal val YearMonth.next: YearMonth
    get() = this.plusMonths(1)

internal val YearMonth.previous: YearMonth
    get() = this.minusMonths(1)