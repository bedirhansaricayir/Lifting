package com.lifting.app.feature.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.core.common.extensions.toLocalDate
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.feature.calendar.models.CalendarDay
import com.lifting.app.feature.calendar.models.CalendarMonth
import com.lifting.app.feature.calendar.models.DayOwner
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private const val WEIGHT_7DAY_WEEK = 1f / 7f

@Composable
fun CalendarMonthItem(
    month: CalendarMonth,
    countsWithDate: List<CountWithDate>,
    selectedDate: LocalDate,
    onClickOnDay: (CalendarDay) -> Unit,
    onClickOnMonth: (CalendarMonth) -> Unit
) {
    val firstDayOfWeek = DayOfWeek.MONDAY

    val dayNames = remember(firstDayOfWeek) {
        val days = (7 - firstDayOfWeek.ordinal)
        var allDays = DayOfWeek.values().toList()
        allDays = allDays.takeLast(days) + allDays.dropLast(days)
        allDays.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
    }

    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dayFormatter = DateTimeFormatter.ofPattern("d")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        CalendarMonthHeader(
            text = month.yearMonth.format(monthFormatter),
            onClick = {
                onClickOnMonth(month)
            }
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            dayNames.forEach { dayName ->
                CalendarDayNameItem(text = dayName)
            }
        }

        month.weekDays.forEach { week ->
            key("${month.yearMonth}_week_${month.weekDays.indexOf(week)}") {
                Row(
                    modifier = Modifier
                        .defaultMinSize(256.dp)
                        .fillMaxWidth()
                ) {
                    for (i in 0..6) {

                        val day = if (i in week.indices) week[i] else null

                        if (day != null && day.owner == DayOwner.THIS_MONTH) {
                            val isToday = day.date == LocalDate.now()
                            val formattedDay = day.date.format(dayFormatter)


                            key("${day.date.dayOfMonth}_${day.date.month.value}_${day.date.year}") {
                                CalendarDayItem(
                                    text = formattedDay,
                                    modifier = Modifier.weight(
                                        WEIGHT_7DAY_WEEK
                                    ),
                                    isSelected = day.date == selectedDate,
                                    isToday = isToday,
                                    dotVisible = countsWithDate.any { it.date.toLocalDate() == day.date },
                                    onClick = {
                                        onClickOnDay(day)
                                    }
                                )
                            }

                        } else {
                            Spacer(modifier = Modifier.weight(WEIGHT_7DAY_WEEK))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ColumnScope.CalendarMonthHeader(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = LiftingTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = LiftingTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(20.dp)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun CalendarYearHeader(year: Year, onClick: () -> Unit) {
    Text(
        text = year.toString(),
        style = LiftingTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = LiftingTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(20.dp)
    )
}

@Composable
fun RowScope.CalendarDayNameItem(text: String) {
    Text(
        text = text,
        style = LiftingTheme.typography.subtitle2,
        color = LiftingTheme.colors.onBackground,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .weight(WEIGHT_7DAY_WEEK)
            .align(Alignment.CenterVertically)
    )
}

@Composable
fun CalendarDayItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    isToday: Boolean = false,
    dotVisible: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(25))
            .clickable(onClick = onClick),
    ) {
        if (isToday || isSelected) {

            var bgModifier = Modifier
                .align(Alignment.Center)
                .size(28.dp)
                .clip(CircleShape)

            if (isToday) {
                bgModifier = bgModifier.border(
                    width = 1.dp,
                    color = LiftingTheme.colors.primary,
                    shape = CircleShape
                )
            }
            if (isSelected) {
                bgModifier = bgModifier.background(LiftingTheme.colors.primary)
            }

            Box(modifier = bgModifier)
        }
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = LiftingTheme.colors.onBackground,
            style = LiftingTheme.typography.subtitle1
        )

        if (dotVisible) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .size(2.dp)
                    .clip(CircleShape)
                    .background(LiftingTheme.colors.primary)
            )
        }
    }
}