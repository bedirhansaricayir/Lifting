package com.lifting.app.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.calendar.components.CalendarMonthItem
import com.lifting.app.feature.calendar.components.CalendarYearHeader
import com.lifting.app.feature.calendar.models.CalendarDay
import com.lifting.app.feature.calendar.models.CalendarMonth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate
import java.time.Year

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

@Composable
internal fun CalendarScreen(
    calendar: LazyPagingItems<Any>,
    workoutsCounts: List<CountWithDate>,
    onNavigateBack: () -> Unit,
    setYearToBackStack: (Int) -> Unit,
    setMonthToBackStack: (CalendarMonth) -> Unit,
    setDayToBackStack: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarScreenContent(
        calendar = calendar,
        workoutsCounts = workoutsCounts,
        onNavigateBack = onNavigateBack,
        setYearToBackStack = setYearToBackStack,
        setMonthToBackStack = setMonthToBackStack,
        setDayToBackStack = setDayToBackStack,
        modifier = modifier
    )
}

@Composable
internal fun CalendarScreenContent(
    calendar: LazyPagingItems<Any>,
    workoutsCounts: List<CountWithDate>,
    onNavigateBack: () -> Unit,
    setYearToBackStack: (Int) -> Unit,
    setMonthToBackStack: (CalendarMonth) -> Unit,
    setDayToBackStack: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier,
) {
    var didFirstAutoScroll by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val today = LocalDate.now()

    suspend fun scrollToToday(smooth: Boolean = true) {
        for (i in 0 until calendar.itemCount) {
            val item = calendar.peek(i)
            if (item is CalendarMonth && item.yearMonth.year == today.year && item.month == today.monthValue) {
                if (smooth) {
                    scrollState.animateScrollToItem(i)
                } else {
                    scrollState.scrollToItem(i)
                }
                break
            }
        }
    }

    LaunchedEffect(calendar.itemCount) {
        calendar.let { calendar ->
            if (calendar.itemCount > 0 && !didFirstAutoScroll) {
                delay(100)
                scrollToToday()
                didFirstAutoScroll = true
            }
        }
    }

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
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.calendar,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = {
                            coroutineScope.launch {
                                scrollToToday()
                            }
                        }
                    )
                },
                navigationIcon = {
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.back,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = onNavigateBack
                    )
                }
            )
        },
        body = {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(LiftingTheme.colors.background)
            ) {
                items(
                    count = calendar.itemCount,
                    key = calendar.itemKey { it },
                    contentType = calendar.itemContentType { "CalendarPagingItems" }
                ) { index ->
                    when (val item = calendar[index]) {
                        is Int -> CalendarYearHeader(
                            year = Year.of(item),
                            onClick = {
                                setYearToBackStack(item)
                            }
                        )

                        is CalendarMonth -> CalendarMonthItem(
                            month = item,
                            countsWithDate = workoutsCounts,
                            selectedDate = today,
                            onClickOnDay = { setDayToBackStack(it) },
                            onClickOnMonth = { setMonthToBackStack(it) }
                        )
                    }
                }
            }
        }
    )
}