package com.lifting.app.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.toLocalDate
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.CountWithDate
import com.lifting.app.core.model.WorkoutWithExtraInfo
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.history.components.HistoryHeader
import com.lifting.app.feature.history.components.HistoryListItem
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate

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
private fun HistoryScreenContent(
    state: HistoryUIState,
    onEvent: (HistoryUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.history),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.calendar,
                            tint = LiftingTheme.colors.onBackground
                        ),
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
