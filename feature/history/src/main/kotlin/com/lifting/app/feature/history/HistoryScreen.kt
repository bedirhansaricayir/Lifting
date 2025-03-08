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
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.history.components.HistoryListItem
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

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
    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.history),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.calendar,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = {}
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
                items(state.data) { workoutInfo ->
                    HistoryListItem(
                        title = workoutInfo.workout?.name.orEmpty(),
                        date = workoutInfo.workout?.startAt ?: workoutInfo.workout?.completedAt
                        ?: workoutInfo.workout?.createdAt,
                        totalExercises = workoutInfo.totalExercises ?: 0,
                        duration = workoutInfo.workout?.duration,
                        volume = workoutInfo.totalVolume,
                        prs = workoutInfo.totalPRs ?: 0,
                        onClick = { /*TODO*/ })
                }
            }
        }
    )
}
