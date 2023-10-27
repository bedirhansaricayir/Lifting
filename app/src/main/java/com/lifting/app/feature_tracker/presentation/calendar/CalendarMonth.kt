package com.lifting.app.feature_tracker.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.lifting.app.common.util.displayText
import com.lifting.app.common.util.toLocaleFormat
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalenderMonth(
    updateCurrMonthViewed : (String) -> Unit,
    updateCurrDateViewed : (String) -> Unit,
    dateEventCount : (String) -> Int,
    checkIfDateSelected : (String) -> Boolean,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
    updateCurrMonthViewed(visibleMonth.yearMonth.displayText())


    Column() {
        HorizontalCalendar(
            state = state,
            monthHeader = { DaysOfWeekTitle(daysOfWeek()) },
            dayContent = {
                val dateToLocaleFormat = it.date.toLocaleFormat()

                Day(
                    day = it,
                    dateToString = dateToLocaleFormat,
                    eventCount = dateEventCount(dateToLocaleFormat),
                    isSelected = checkIfDateSelected(dateToLocaleFormat),
                    updateCurrDateViewed = updateCurrDateViewed,
                )
            }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    dateToString: String,
    eventCount: Int,
    isSelected: Boolean,
    updateCurrDateViewed: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val localDate = remember { LocalDate.now() }
    val currDate = day.date == localDate

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    updateCurrDateViewed(dateToString)
                },
            ),
        contentAlignment = Alignment.Center,
    ) {

        Box(modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .size(32.dp)
            .border(
                width = 1.dp,
                color = if (currDate) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            )
            .background(
                if (currDate && !isSelected) Color.Transparent
                else if (isSelected) MaterialTheme.colorScheme.primary
                else Color.Transparent
            ),
            contentAlignment = Alignment.Center
        ){
            DayDecor(day = day, isSelected)
        }

        if(eventCount > 0){
            EventMark(evenCount = eventCount)
        }
    }
}


@Composable
fun DayDecor(day : CalendarDay, isSelected: Boolean) {
    val currentDate = remember { LocalDate.now() }

    val color = when(day.position) {
        DayPosition.MonthDate -> {
            when {
                (day.date == currentDate && isSelected) || isSelected -> DarkGray

                day.date == currentDate -> Color(0XFFF0F0F0)
                //Color(0XFF909090)

                else -> Color(0XFFF0F0F0)
                //Color(0XFF909090)

            }
        }
        DayPosition.InDate -> Color(0XFF909090)
        DayPosition.OutDate -> Color(0XFF909090)
    }

    val fontWeight = when {
        day.date == currentDate || isSelected -> FontWeight.Bold

        else -> FontWeight.Normal

    }

    Text(text = day.date.dayOfMonth.toString(),
        color = color,
        fontWeight = fontWeight,
        fontSize = 12.sp
    )
}