@file:OptIn(ExperimentalToolbarApi::class)

package com.lifting.app.feature.workout

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.TimePeriod
import com.lifting.app.core.model.isList
import com.lifting.app.core.model.opposite
import com.lifting.app.core.ui.components.LiftingAnimationContainer
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.extensions.toCreateWorkoutNameByPeriod
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.workout.components.TransitionableLazyList
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.FabPosition
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.ToolbarWithFabScaffold
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
private fun WorkoutScreenContent(
    state: WorkoutUIState,
    onEvent: (WorkoutUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current

    ToolbarWithFabScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(id = com.lifting.app.core.ui.R.string.top_bar_title_workout),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this,
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = if (state.layoutType.isList()) LiftingTheme.icons.grid else LiftingTheme.icons.list,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutUIEvent.OnLayoutTypeClicked(state.layoutType.opposite())) }
                    )
                }
            )
        },
        fab = {
            LiftingAnimationContainer(
                visible = state.fabIsVisible && state.isExistActiveWorkout.not(),
                enterTransition = slideInVertically(initialOffsetY = { it * 2 }),
                exitTransition = slideOutVertically(targetOffsetY = { it * 2 }),
                content = {
                    LiftingButton(
                        buttonType = LiftingButtonType.ExtendedButton(
                            content = {
                                Icon(
                                    imageVector = LiftingTheme.icons.play,
                                    contentDescription = String.EMPTY,
                                    modifier = Modifier.padding(end = LiftingTheme.dimensions.small)
                                )
                                Text(
                                    text = stringResource(id = com.lifting.app.core.ui.R.string.start_workout),
                                    style = LiftingTheme.typography.button,
                                )
                            }
                        ),
                        onClick = { onEvent(WorkoutUIEvent.OnStartWorkoutClicked(TimePeriod.now().toCreateWorkoutNameByPeriod(context))) }
                    )
                }
            )
        },
        fabPosition = FabPosition.Center
    ) {
        TransitionableLazyList(
            data = state.templates,
            layoutType = state.layoutType,
            onCreateTemplateClicked = { onEvent(WorkoutUIEvent.OnCreateTemplateClicked) },
            onTemplateClicked = { onEvent(WorkoutUIEvent.OnTemplateClicked(it)) },
            onScrollDirectionChanged = { onEvent(WorkoutUIEvent.OnScrollDirectionChanged(it)) }
        )
    }
}

