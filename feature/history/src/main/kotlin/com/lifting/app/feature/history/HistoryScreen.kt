package com.lifting.app.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.common.extensions.toLocalDate
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.HorizontalSlidingAnimationContainer
import com.lifting.app.core.ui.components.LiftingChip
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.history.components.HistoryHeader
import com.lifting.app.feature.history.components.HistoryListItem
import com.lifting.app.feature.history.components.WorkoutsDateRangeType
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

@Composable
internal fun HistoryScreen(
    state: HistoryUIState,
    onEvent: (HistoryUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    HistoryScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun HistoryScreenContent(
    state: HistoryUIState,
    onEvent: (HistoryUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        HistoryUIState.Error -> {}
        HistoryUIState.Loading -> {}
        is HistoryUIState.Success ->
            HistoryScreenSuccess(
                state = state,
                onEvent = onEvent,
                modifier = modifier
            )
    }
}

@Composable
internal fun HistoryScreenSuccess(
    state: HistoryUIState.Success,
    onEvent: (HistoryUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val chipString = when (state.dateRangeType) {
        is WorkoutsDateRangeType.Year -> state.dateRangeType.year.toString()
        is WorkoutsDateRangeType.Month -> YearMonth.of(
            state.dateRangeType.year,
            state.dateRangeType.month
        ).format(DateTimeFormatter.ofPattern("MMMM yyyy")).toString()

        is WorkoutsDateRangeType.Day -> LocalDate.of(
            state.dateRangeType.year,
            state.dateRangeType.month,
            state.dateRangeType.day
        ).format(DateTimeFormatter.ofPattern("MMM d, yyyy")).toString()

        WorkoutsDateRangeType.All -> null
    }

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.history),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    HorizontalSlidingAnimationContainer(
                        visible = chipString.isNullOrEmpty().not(),
                        content = {
                            LiftingChip(
                                onClick = {},
                                trailingIcon = {
                                    LiftingIconButton(
                                        modifier = Modifier.size(LiftingTheme.dimensions.large),
                                        imageVector = LiftingTheme.icons.close,
                                        contentDescription = String.EMPTY,
                                        tint = LiftingTheme.colors.onPrimary,
                                        onClick = { onEvent(HistoryUIEvent.OnChipRemoveClicked) }
                                    )
                                }
                            ) {
                                Text(
                                    text = chipString.orEmpty(),
                                    style = LiftingTheme.typography.caption,
                                )
                            }
                        }
                    )

                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.calendar,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = { onEvent(HistoryUIEvent.OnCalendarClicked) }
                    )
                }
            )
        },
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LiftingTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large),
                contentPadding = PaddingValues(LiftingTheme.dimensions.large),
            ) {
                items(
                    state.data,
                    key = {
                        when (it) {
                            is CountWithDate -> it.date.toString()
                            is WorkoutWithExtraInfo -> it.workout!!.id
                            is Long -> it.toString()
                            else -> generateUUID()
                        }
                    }
                ) { data ->
                    when (data) {
                        is WorkoutWithExtraInfo -> {
                            HistoryListItem(
                                title = data.workout?.name.orEmpty(),
                                date = data.workout?.startAt ?: data.workout?.completedAt
                                ?: data.workout?.createdAt,
                                totalExercises = data.totalExercises ?: 0,
                                duration = data.workout?.duration,
                                volume = data.totalVolume,
                                prs = data.totalPRs ?: 0,
                                onClick = { onEvent(HistoryUIEvent.OnWorkoutClicked(data.workout?.id!!)) }
                            )
                        }

                        is Long -> {
                            HistoryHeader(
                                title = null,
                                totalWorkouts = data.toInt()
                            )
                        }

                        is CountWithDate -> {
                            HistoryHeader(
                                date = data.date.toLocalDate() ?: LocalDate.now(),
                                totalWorkout = data.count.toInt()
                            )
                        }
                    }
                }
            }
        }
    )
}
