package com.lifting.app.feature.workout_template_preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.toReadableFormat
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingAlertDialog
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.extensions.toLocalizedMuscleName
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@Composable
internal fun WorkoutTemplatePreviewScreen(
    state: WorkoutTemplatePreviewUIState,
    onEvent: (WorkoutTemplatePreviewUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    WorkoutTemplatePreviewContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun WorkoutTemplatePreviewContent(
    state: WorkoutTemplatePreviewUIState,
    onEvent: (WorkoutTemplatePreviewUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolBarScaffold(
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            LiftingTopBar(
                title = state.workout?.name.orEmpty().ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) } ,
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                navigationIcon = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.back,
                            tint = LiftingTheme.colors.onBackground
                        ),
                        onClick = { onEvent(WorkoutTemplatePreviewUIEvent.OnBackClicked) }
                    )
                },
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.edit,
                            tint = LiftingTheme.colors.onBackground
                        ),
                        onClick = {
                            state.workout?.id?.let {
                                onEvent(WorkoutTemplatePreviewUIEvent.OnEditClicked(it))
                            }
                        }
                    )

                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.delete,
                            tint = LiftingTheme.colors.onBackground
                        ),
                        onClick = { onEvent(WorkoutTemplatePreviewUIEvent.OnDeleteClicked) }
                    )

                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.play,
                            tint = LiftingTheme.colors.onBackground
                        ),
                        onClick = { onEvent(WorkoutTemplatePreviewUIEvent.OnPlayClicked) }
                    )
                }
            )
        },
        body = {
            val lastPerformedStr = state.template?.lastPerformedAt?.toReadableFormat()
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(LiftingTheme.dimensions.large)
            ) {
                lastPerformedStr?.let { lastPerformed ->
                    item {
                        Text(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.last_performed_at),
                            style = LiftingTheme.typography.caption.copy(
                                color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
                            )
                        )
                        Text(
                            text = lastPerformed,
                            style = LiftingTheme.typography.caption.copy(
                                color = LiftingTheme.colors.onBackground
                            )
                        )
                    }
                }

                items(state.entries) {
                    TemplatePreviewExerciseComponent(
                        name = "${it.logEntries.size} x ${it.exercise.name}",
                        muscle = it.exercise.primaryMuscleTag?.let {
                            stringResource(it.toLocalizedMuscleName())
                        },
                        onClick = {
                            //TODO: Navigate to exercise detail screen
                        }
                    )
                }
            }
        }
    )

    if (state.showActiveWorkoutDialog) {
        LiftingAlertDialog(
            title = com.lifting.app.core.ui.R.string.workout_in_progress,
            text = com.lifting.app.core.ui.R.string.workout_in_progress_description,
            dismissText = com.lifting.app.core.ui.R.string.cancel,
            confirmText = com.lifting.app.core.ui.R.string.discard,
            onDismiss = { onEvent(WorkoutTemplatePreviewUIEvent.OnDialogDismissClicked) },
            onConfirm = { onEvent(WorkoutTemplatePreviewUIEvent.OnDialogConfirmClicked) }
        )
    }

}

@Composable
private fun TemplatePreviewExerciseComponent(
    name: String,
    muscle: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LiftingTheme.dimensions.large,
                vertical = LiftingTheme.dimensions.medium,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
        ) {
            Text(
                text = name,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground
            )
            muscle?.let {
                Text(
                    text = it,
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f)
                )
            }
        }

        LiftingButton(
            buttonType = LiftingButtonType.IconButton(
                icon = LiftingTheme.icons.info,
                tint = LiftingTheme.colors.onBackground
            ),
            onClick = onClick
        )
    }
}