@file:OptIn(ExperimentalLayoutApi::class)

package com.lifting.app.feature.workout_panel.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.feature.workout_panel.WorkoutPanelUIState
import com.lifting.app.feature.workout_panel.WorkoutPanelViewModel

/**
 * Created by bedirhansaricayir on 07.05.2025
 */

@Composable
internal fun WorkoutQuickInfo(
    currentDuration: String,
    currentVolume: String,
    currentSets: String
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = LiftingTheme.dimensions.large,
                end = LiftingTheme.dimensions.large,
                bottom = LiftingTheme.dimensions.large,
                top = LiftingTheme.dimensions.small
            )
    ) {
        InfoItem(
            value = currentDuration,
            title = stringResource(id = com.lifting.app.core.ui.R.string.duration2)
        )
        InfoItem(
            value = currentVolume + "kg",
            title = stringResource(id = com.lifting.app.core.ui.R.string.volume)
        )
        InfoItem(
            value = currentSets,
            title = stringResource(id = com.lifting.app.core.ui.R.string.sets)
        )
    }
}

@Composable
private fun InfoItem(value: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            style = LiftingTheme.typography.subtitle1,
            color = LiftingTheme.colors.onBackground
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = LiftingTheme.typography.subtitle2,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun PanelTopCollapsed(
    modifier: Modifier = Modifier,
    viewModel: WorkoutPanelViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state is WorkoutPanelUIState.Success) {
        with(state as WorkoutPanelUIState.Success) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = LiftingTheme.dimensions.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                this@with.workout.name?.let {
                    Text(
                        text = it,
                        style = LiftingTheme.typography.header2,
                        color = LiftingTheme.colors.onBackground
                    )
                }
                Text(
                    text = this@with.currentDuration,
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground
                )
            }
        }

    }
}

@Composable
fun PanelTopExpanded(
    elapsedTime: Long?,
    totalTime: Long?,
    isRunning: Boolean,
    timeString: String?,
    onTimerBtnClicked: () -> Unit,
    onCollapseButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutPanelViewModel = hiltViewModel(),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LiftingTheme.dimensions.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LiftingButton(
            buttonType = LiftingButtonType.IconButton(
                icon = LiftingTheme.icons.chevronDown,
                tint = LiftingTheme.colors.onBackground,
            ),
            onClick = onCollapseButtonClicked,
        )

        RestTimerButton(
            restTimerElapsedTime = elapsedTime,
            restTimerTotalTime = totalTime,
            onTimerBtnClicked = onTimerBtnClicked,
            isTimerRunning = isRunning,
            restTimerTimeString = timeString
        )
        Spacer(modifier = Modifier.weight(1f))

        LiftingButton(
            buttonType = LiftingButtonType.TextButton(text = stringResource(com.lifting.app.core.ui.R.string.finish)),
            onClick = viewModel::finishWorkout,
        )
    }
}

@Composable
private fun RestTimerButton(
    restTimerElapsedTime: Long?,
    restTimerTotalTime: Long?,
    isTimerRunning: Boolean,
    restTimerTimeString: String?,
    onTimerBtnClicked: () -> Unit,
) {
    val contentColor by animateColorAsState(
        targetValue = if (isTimerRunning)
            LiftingTheme.colors.onPrimary
        else
            LiftingTheme.colors.onBackground
    )

    var parentModifier = Modifier
        .width(100.dp)
        .clip(LiftingTheme.shapes.small)


    var iconModifier = Modifier
        .padding(8.dp)

    if (isTimerRunning) {
        parentModifier = parentModifier.clickable(onClick = onTimerBtnClicked)
    } else {
        iconModifier = iconModifier.clickable(onClick = onTimerBtnClicked)
    }

    Box(
        modifier = parentModifier
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .matchParentSize()
                .width(100.dp),
            enter = slideInHorizontally(
                initialOffsetX = { -it }),
            exit = slideOutHorizontally(
                targetOffsetX = { -it }),
            visible = restTimerElapsedTime != null && restTimerTotalTime != null && isTimerRunning
        ) {

            LinearProgressIndicator(
                modifier = Modifier
                    .matchParentSize()
                    .width(100.dp),
                progress = (
                        (restTimerElapsedTime ?: 0L).toFloat() / (restTimerTotalTime
                            ?: 1L).toFloat()).coerceIn(0f, 1f)
                    .takeIf { !it.isNaN() } ?: 0f,
                color = LiftingTheme.colors.primary,
                backgroundColor = LiftingTheme.colors.primary.copy(alpha = 0.5f)
            )
        }
        Box(modifier = Modifier) {
            Icon(
                modifier = iconModifier
                    .align(Alignment.CenterStart),
                imageVector = LiftingTheme.icons.timer,
                contentDescription = String.EMPTY,
                tint = contentColor
            )

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(),
                visible = isTimerRunning && restTimerTimeString != null
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        text = restTimerTimeString ?: "",
                        fontSize = 14.sp,
                        color = contentColor
                    )
                }
            }
        }
    }
}