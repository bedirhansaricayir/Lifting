package com.lifting.app.feature.exercises

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.common.extensions.clearFocus
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.core.ui.components.LiftingSearchField
import com.lifting.app.core.ui.exercises.ExerciseItem
import com.lifting.app.core.ui.extensions.detectGesturesThenHandleFocus
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

@Composable
internal fun ExercisesScreen(
    modifier: Modifier = Modifier,
    state: ExercisesUIState,
    onEvent: (ExercisesUIEvent) -> Unit,
    isBottomSheet: Boolean,
) {
    ExercisesScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent,
        isBottomSheet = isBottomSheet
    )
}

@Composable
internal fun ExercisesScreenContent(
    modifier: Modifier = Modifier,
    state: ExercisesUIState,
    onEvent: (ExercisesUIEvent) -> Unit,
    isBottomSheet: Boolean,
) {
    when (state) {
        ExercisesUIState.Loading -> {}

        is ExercisesUIState.Success -> ListScreen(
            modifier = modifier,
            state = state,
            onEvent = onEvent,
            isBottomSheet = isBottomSheet
        )

        is ExercisesUIState.Error -> {}
    }
}

@Composable
internal fun ListScreen(
    modifier: Modifier = Modifier,
    state: ExercisesUIState.Success,
    onEvent: (ExercisesUIEvent) -> Unit,
    isBottomSheet: Boolean,
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    CollapsingToolBarScaffold(
        modifier = modifier
            .background(LiftingTheme.colors.background)
            .detectGesturesThenHandleFocus(),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.add,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = {
                            onEvent(ExercisesUIEvent.OnAddClick)
                                .clearFocus(keyboard, focusManager)
                        }
                    )
                }
            )
        },
        body = {
            ExerciseList(
                exercisesWithHeader = state.groupedExercises.orEmpty(),
                searchSection = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LiftingTheme.dimensions.large),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LiftingSearchField(
                            modifier = Modifier.weight(0.75f),
                            value = state.searchQuery,
                            onValueChange = {
                                onEvent(ExercisesUIEvent.OnSearchQueryChanged(it))
                            }
                        )

                        Spacer(Modifier.width(LiftingTheme.dimensions.large))

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(
                                    border = BorderStroke(1.dp, LiftingTheme.colors.onBackground),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            LiftingIconButton(
                                painterRes = LiftingTheme.icons.filter,
                                contentDescription = String.EMPTY,
                                tint = LiftingTheme.colors.onBackground,
                                onClick = {
                                    onEvent(ExercisesUIEvent.OnFilterClick)
                                        .clearFocus(keyboard, focusManager)
                                }
                            )
                        }

                    }
                },
                onExerciseClick = { onEvent(ExercisesUIEvent.OnExerciseClick(it, isBottomSheet)) }
            )
        }
    )
}

@Composable
internal fun ExerciseList(
    modifier: Modifier = Modifier,
    searchSection: @Composable () -> Unit,
    exercisesWithHeader: Map<String, List<ExerciseWithInfo>>,
    onExerciseClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            searchSection.invoke()
        }

        exercisesWithHeader.map { entry ->
            stickyHeader(key = entry.key) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .animateItem(),
                    text = entry.key,
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground.copy(0.75f)
                )
            }

            items(
                items = entry.value,
                key = { it.exercise.exerciseId }
            ) { exercise ->
                ExerciseItem(
                    modifier = Modifier.animateItem(),
                    exerciseImage = null,
                    exerciseName = exercise.exercise.name ?: "",
                    exerciseType = exercise.muscle?.name ?: "",
                    exerciseLogCount = exercise.logsCount,
                    onClick = { onExerciseClick(exercise.exercise.exerciseId) }
                )
            }
        }
    }
}