package com.lifting.app.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingAlertDialog
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.calendar.components.DayItem
import com.lifting.app.feature.calendar.components.DaysOfWeekTitle
import com.lifting.app.feature.calendar.components.EmptyCalendarItem
import com.lifting.app.feature.calendar.components.HistoryListItem
import com.lifting.app.feature.calendar.components.LiftingCalendar
import com.lifting.app.feature.calendar.components.rememberCalendarState
import com.lifting.app.feature.calendar.components.yearMonth
import kotlinx.coroutines.launch
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

@Composable
internal fun CalendarScreen(
    state: CalendarUIState,
    onEvent: (CalendarUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun CalendarScreenContent(
    state: CalendarUIState,
    onEvent: (CalendarUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val scope = rememberCoroutineScope()
    val today by lazy { LocalDate.now() }
    val calendarState = rememberCalendarState(
        startMonth = state.calendarConfig.startMonth,
        endMonth = state.calendarConfig.endMonth,
        firstVisibleMonth = state.calendarConfig.currentMonth,
    )

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.calendar),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.revert,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = {
                            scope.launch {
                                calendarState.animateScrollToMonth(today.yearMonth)
                            }
                            onEvent(CalendarUIEvent.OnDayClicked(today))
                        }
                    )
                },
                navigationIcon = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.back,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(CalendarUIEvent.OnBackIconClick) },
                    )
                }
            )
        },
        body = {
            LazyColumn(
                modifier = Modifier
                    .background(LiftingTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item(key = "lifting_calendar") {
                    LiftingCalendar(
                        state = calendarState,
                        dayContent = { day ->
                            DayItem(
                                day = day,
                                selected = state.selectedDay == day.date,
                                indicatorCount = state.workoutDays.count { it == day.date },
                                onClick = { onEvent(CalendarUIEvent.OnDayClicked(it)) }
                            )
                        },
                        monthHeader = { month ->
                            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                        }
                    )
                }

                itemsIndexed(
                    items = state.workouts,
                    key = { _, workout -> workout.workout!!.id }
                ) { index, data ->
                    HistoryListItem(
                        modifier = Modifier.padding(horizontal = LiftingTheme.dimensions.large),
                        title = data.workout?.name.orEmpty(),
                        date = data.workout?.startAt ?: data.workout?.completedAt
                        ?: data.workout?.createdAt,
                        totalExercises = data.totalExercises ?: 0,
                        duration = data.workout?.duration,
                        volume = data.totalVolume,
                        prs = data.totalPRs ?: 0,
                        onClick = { onEvent(CalendarUIEvent.OnWorkoutClicked(data.workout?.id!!)) }
                    )
                }

                if (state.selectedDay == LocalDate.now() && state.workouts.isEmpty()) {
                    item {
                        EmptyCalendarItem(
                            onClick = { onEvent(CalendarUIEvent.OnCreateNewWorkoutClicked) }
                        )
                    }
                }
            }

            if (state.showActiveWorkoutDialog) {
                LiftingAlertDialog(
                    title = com.lifting.app.core.ui.R.string.workout_in_progress,
                    text = com.lifting.app.core.ui.R.string.workout_in_progress_description,
                    dismissText = com.lifting.app.core.ui.R.string.cancel,
                    confirmText = com.lifting.app.core.ui.R.string.discard,
                    onDismiss = { onEvent(CalendarUIEvent.OnDialogDismissClicked) },
                    onConfirm = { onEvent(CalendarUIEvent.OnDialogConfirmClicked) }
                )
            }
        }
    )
}