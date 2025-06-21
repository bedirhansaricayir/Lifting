package com.lifting.app.feature.calendar.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.feature.calendar.components.CalendarDefaults.flingBehavior
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Created by bedirhansaricayir on 06.06.2025
 */

@Composable
fun LiftingCalendar(
    state: CalendarState,
    dayContent: @Composable (BoxScope.(CalendarDay) -> Unit),
    monthHeader: @Composable (ColumnScope.(CalendarMonth) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = state.lastVisibleMonth.yearMonth.getDisplayName(),
            textAlign = TextAlign.Center,
            style = LiftingTheme.typography.header2,
            color = LiftingTheme.colors.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LiftingTheme.dimensions.medium)
        )

        HorizontalCalendar(
            modifier = modifier,
            state = state,
            dayContent = dayContent,
            monthHeader = monthHeader,
        )
    }
}

@Composable
internal fun DayItem(
    day: CalendarDay,
    selected: Boolean,
    modifier: Modifier = Modifier,
    indicatorCount: Int = 0,
    onClick: (LocalDate) -> Unit = {}
) {
    val isToday = day.date == LocalDate.now()
    val dayInCurrentMonth = day.position == DayPosition.MonthDate

    val dotColor by animateColorAsState(
        targetValue = when {
            selected -> LiftingTheme.colors.onPrimary
            else -> LiftingTheme.colors.primary
        },
        label = "dot_color"
    )

    val backgroundColor by animateColorAsState(
        targetValue = when {
            selected -> LiftingTheme.colors.primary
            else -> Color.Transparent
        },
        label = "background_color"
    )

    val textColor by animateColorAsState(
        targetValue = when {
            selected -> LiftingTheme.colors.onPrimary
            isToday || dayInCurrentMonth -> LiftingTheme.colors.onBackground
            else -> LiftingTheme.colors.onBackground.copy(alpha = 0.3f)
        },
        label = "text_color"
    )

    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .size(40.dp)
            .clip(CircleShape)
            .clickable(
                enabled = dayInCurrentMonth,
                onClick = { onClick(day.date) }
            ),
        contentAlignment = Alignment.Center
    ) {
        if ((selected || isToday) && dayInCurrentMonth) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .then(
                        if (!selected) Modifier.border(
                            width = 1.dp,
                            color = LiftingTheme.colors.primary,
                            shape = CircleShape
                        ) else Modifier
                    )
            )
        }

        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            textAlign = TextAlign.Center,
            style = LiftingTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        )

        if (indicatorCount != 0 && dayInCurrentMonth) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(indicatorCount) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }
        }
    }
}

@Composable
internal fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
private fun HorizontalCalendar(
    modifier: Modifier,
    state: CalendarState,
    calendarScrollPaged: Boolean = true,
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    contentHeightMode: ContentHeightMode = ContentHeightMode.Wrap,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)? = null,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)? = null,
) {
    LazyRow(
        modifier = modifier,
        state = state.listState,
        flingBehavior = flingBehavior(calendarScrollPaged, state.listState),
        userScrollEnabled = userScrollEnabled,
        reverseLayout = reverseLayout,
        contentPadding = contentPadding,
    ) {
        CalendarMonths(
            monthCount = state.calendarInfo.indexCount,
            monthData = state.store::get,
            contentHeightMode = contentHeightMode,
            dayContent = dayContent,
            monthHeader = monthHeader,
            monthBody = monthBody,
            monthFooter = monthFooter,
            monthContainer = monthContainer,
            onItemPlaced = state.placementInfo::onItemPlaced,
        )
    }
}