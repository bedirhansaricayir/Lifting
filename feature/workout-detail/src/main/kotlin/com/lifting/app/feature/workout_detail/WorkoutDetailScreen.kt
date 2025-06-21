package com.lifting.app.feature.workout_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingAlertDialog
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.PersonalRecordsRowComponent
import com.lifting.app.core.ui.components.SessionExerciseCardItem
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.workout_detail.components.SessionQuickInfo
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 06.04.2025
 */
@Composable
internal fun WorkoutDetailScreen(
    state: WorkoutDetailUIState,
    onEvent: (WorkoutDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    WorkoutDetailScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun WorkoutDetailScreenContent(
    state: WorkoutDetailUIState,
    onEvent: (WorkoutDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolBarScaffold(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background),
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            LiftingTopBar(
                title = state.workout?.name
                    ?: stringResource(id = com.lifting.app.core.ui.R.string.workout),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                navigationIcon = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.back,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutDetailUIEvent.OnBackClicked) }
                    )
                },
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.edit,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutDetailUIEvent.OnEditClicked) }
                    )

                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.delete,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutDetailUIEvent.OnDeleteClicked) }
                    )

                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.replay,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(WorkoutDetailUIEvent.OnReplayClicked) }
                    )
                }
            )
        },
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LiftingTheme.colors.background),
                contentPadding = PaddingValues(LiftingTheme.dimensions.large)
            ) {
                if (state.workout?.personalRecords.isNullOrEmpty().not()) {
                    item(key = "prs") {
                        PersonalRecordsRowComponent(
                            modifier = Modifier
                                .padding(bottom = 10.dp),
                            prs = state.workout.personalRecords!!
                        )
                    }
                }

                item("quick_info") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = pluralStringResource(
                                    id = com.lifting.app.core.ui.R.plurals.number_of_exercises,
                                    state.logs.size,
                                    state.logs.size,
                                ),
                                color = LiftingTheme.colors.onBackground,
                            )
                            Spacer(Modifier.height(LiftingTheme.dimensions.extraSmall))
                            SessionQuickInfo(
                                duration = state.workout?.duration,
                                volume = state.volume,
                                prs = state.pr
                            )
                        }
                    }
                }



                items(state.logs, key = { it.junction.id }) { log ->
                    SessionExerciseCardItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        supersetId = log.junction.supersetId,
                        title = log.exercise.name ?: "",
                        exerciseCategory = log.exercise.category,
                        entries = log.logEntries,
                        notes = log.notes
                    )
                }
            }

            if (state.showActiveWorkoutDialog) {
                LiftingAlertDialog(
                    title = com.lifting.app.core.ui.R.string.workout_in_progress,
                    text = com.lifting.app.core.ui.R.string.workout_in_progress_description,
                    dismissText = com.lifting.app.core.ui.R.string.cancel,
                    confirmText = com.lifting.app.core.ui.R.string.discard,
                    onDismiss = { onEvent(WorkoutDetailUIEvent.OnDialogDismissClicked) },
                    onConfirm = { onEvent(WorkoutDetailUIEvent.OnDialogConfirmClicked) }
                )
            }
        }
    )
}
