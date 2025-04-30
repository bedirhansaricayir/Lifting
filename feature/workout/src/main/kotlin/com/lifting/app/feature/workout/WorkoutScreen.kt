package com.lifting.app.feature.workout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingTextButton
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.workout.components.TransitionableLazyList
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

@Composable
internal fun WorkoutScreen(
    state: WorkoutUIState,
    onEvent: (WorkoutUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    WorkoutScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun WorkoutScreenContent(
    state: WorkoutUIState,
    onEvent: (WorkoutUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        WorkoutUIState.Error -> {}
        WorkoutUIState.Loading -> {}
        is WorkoutUIState.Success ->
            WorkoutScreenSuccess(
                modifier = modifier,
                state = state,
                onEvent = onEvent
            )
    }
}

@Composable
internal fun WorkoutScreenSuccess(
    state: WorkoutUIState.Success,
    onEvent: (WorkoutUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    var isButtonPinned by remember { mutableStateOf(false) }

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.top_bar_title_workout),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    AnimatedVisibility(visible = isButtonPinned) {
                        LiftingTextButton(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.create_template),
                            onClick = {
                                onEvent(WorkoutUIEvent.OnCreateTemplateClicked)
                            }
                        )
                    }
                }
            )
        },
        body = {
            TransitionableLazyList(
                data = state.templates,
                onCreateTemplateClicked = { onEvent(WorkoutUIEvent.OnCreateTemplateClicked) },
                onButtonVisibilityChanged = { isButtonPinned = it },
                onTemplateClicked = { onEvent(WorkoutUIEvent.OnTemplateClicked(it)) }
            )
        }
    )
}

