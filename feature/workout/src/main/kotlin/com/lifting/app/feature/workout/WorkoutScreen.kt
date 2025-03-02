package com.lifting.app.feature.workout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingTextButton
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.workout.components.WorkoutTemplateCard
import com.lifting.app.feature.workout.components.WorkoutTemplateSectionHeader
import com.lifting.app.feature.workout.components.liftingTemplates
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
            WorkoutScreenSuccessBody(
                state = state,
                onCreateTemplateClicked = {
                    onEvent(WorkoutUIEvent.OnCreateTemplateClicked)
                },
                onButtonVisibilityChanged = { isPinned ->
                    isButtonPinned = isPinned
                },
                onTemplateClicked = {
                    onEvent(WorkoutUIEvent.OnTemplateClicked(it))
                }
            )
        }
    )
}

@Composable
internal fun WorkoutScreenSuccessBody(
    state: WorkoutUIState.Success,
    onTemplateClicked: (String) -> Unit,
    onCreateTemplateClicked: () -> Unit,
    onButtonVisibilityChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyStaggeredGridState()

    val isButtonPinned by remember(listState) {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                    listState.firstVisibleItemScrollOffset > 100
        }
    }

    LaunchedEffect(isButtonPinned) {
        onButtonVisibilityChanged(isButtonPinned)
    }

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background)
            .padding(horizontal = LiftingTheme.dimensions.large),
        state = listState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = LiftingTheme.dimensions.extraSmall,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall),
    ) {
        item(
            span = StaggeredGridItemSpan.FullLine
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WorkoutTemplateSectionHeader(
                    text = stringResource(id = com.lifting.app.core.ui.R.string.my_templates)
                )

                AnimatedVisibility(visible = isButtonPinned.not()) {
                    LiftingTextButton(
                        text = stringResource(id = com.lifting.app.core.ui.R.string.create_template),
                        onClick = onCreateTemplateClicked
                    )
                }
            }

        }
        itemsIndexed(state.templates) { index, item ->
            WorkoutTemplateCard(
                cardShape = LiftingTheme.shapes.gridShapes(
                    listSize = state.templates.size,
                    index = index
                ),
                title = item.workout.name.orEmpty().ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) },
                exercisesInfo = stringResource(
                    id = com.lifting.app.core.ui.R.string.exercises_count,
                    item.junctions.size
                ),
                onClick = { onTemplateClicked(item.template.id) }
            )
        }

        item(
            span = StaggeredGridItemSpan.FullLine
        ) {
            WorkoutTemplateSectionHeader(
                text = stringResource(id = com.lifting.app.core.ui.R.string.lifting_templates)
            )
        }

        itemsIndexed(liftingTemplates) { index, item ->
            WorkoutTemplateCard(
                cardShape = LiftingTheme.shapes.gridShapes(
                    listSize = liftingTemplates.size,
                    index = index
                ),
                title = item.title,
                description = item.description,
                exercisesInfo = stringResource(
                    id = com.lifting.app.core.ui.R.string.exercises_count,
                    item.exerciseList.size
                )
            )
        }
    }
}