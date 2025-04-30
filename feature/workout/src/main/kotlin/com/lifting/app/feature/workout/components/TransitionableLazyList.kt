package com.lifting.app.feature.workout.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.TemplateWithWorkout
import com.lifting.app.core.ui.components.LiftingTextButton

/**
 * Created by bedirhansaricayir on 27.04.2025
 */

@Composable
fun TransitionableLazyList(
    data: List<TemplateWithWorkout>,
    onCreateTemplateClicked: () -> Unit,
    onButtonVisibilityChanged: (Boolean) -> Unit,
    onTemplateClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyStaggeredGridState()
    val columnState = rememberLazyListState()
    var layoutType by remember { mutableStateOf(LayoutType.Grid) }

    val transition = updateTransition(targetState = layoutType)

    val gridAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500) },
    ) { state ->
        if (state == LayoutType.Grid) 1f  else 0f
    }

    val listAlpha by transition.animateFloat(
        transitionSpec = { tween(500) },
        label = "ListAlpha"
    ) { state ->
        if (state == LayoutType.List) 1f else 0f
    }

    val isButtonPinned by remember(gridState, columnState,layoutType) {
        derivedStateOf {
            when (layoutType) {
                LayoutType.Grid -> gridState.firstVisibleItemIndex > 0 || gridState.firstVisibleItemScrollOffset > 100
                LayoutType.List -> columnState.firstVisibleItemIndex > 0 || columnState.firstVisibleItemScrollOffset > 100
            }
        }
    }

    LaunchedEffect(isButtonPinned) {
        onButtonVisibilityChanged(isButtonPinned)
    }

    AnimatedVisibility(
        visible = listAlpha > 0f,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.then(Modifier.alpha(listAlpha))
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(LiftingTheme.colors.background)
                .padding(horizontal = LiftingTheme.dimensions.large),
            state = columnState,
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WorkoutTemplateSectionHeader(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = com.lifting.app.core.ui.R.string.my_templates)
                    )

                    AnimatedVisibility(visible = isButtonPinned.not()) {
                        LiftingTextButton(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.create_template),
                            onClick = onCreateTemplateClicked
                        )
                    }

                    /*LiftingIconButton(
                        painterRes = if (layoutType == LayoutType.Grid) LiftingTheme.icons.column else LiftingTheme.icons.grid,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = { layoutType = layoutType.opposite() }
                    )*/
                }
            }

            itemsIndexed(data) { index, item ->
                WorkoutTemplateCard(
                    modifier = Modifier.fillMaxWidth(),
                    cardShape = LiftingTheme.shapes.listShapes(
                        listSize = data.size,
                        index = index
                    ),
                    title = item.workout.name.orEmpty().ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) },
                    description = item.workout.note,
                    exercisesInfo = stringResource(
                        id = com.lifting.app.core.ui.R.string.exercises_count,
                        item.junctions.size
                    ),
                    onClick = { onTemplateClicked(item.template.id) }
                )
            }

            item {
                WorkoutTemplateSectionHeader(
                    text = stringResource(id = com.lifting.app.core.ui.R.string.lifting_templates)
                )
            }

            itemsIndexed(liftingTemplates) { index, item ->
                WorkoutTemplateCard(
                    modifier = Modifier.fillMaxWidth(),
                    cardShape = LiftingTheme.shapes.listShapes(
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

    AnimatedVisibility(
        visible = gridAlpha > 0f,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.then(Modifier.alpha(gridAlpha))
    ) {
        LazyVerticalStaggeredGrid(
            modifier = modifier
                .fillMaxSize()
                .background(LiftingTheme.colors.background)
                .padding(horizontal = LiftingTheme.dimensions.large),
            state = gridState,
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WorkoutTemplateSectionHeader(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = com.lifting.app.core.ui.R.string.my_templates)
                    )

                    AnimatedVisibility(visible = isButtonPinned.not()) {
                        LiftingTextButton(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.create_template),
                            onClick = onCreateTemplateClicked
                        )
                    }

                    /*LiftingIconButton(
                        painterRes = if (layoutType == LayoutType.Grid) LiftingTheme.icons.column else LiftingTheme.icons.grid,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = { layoutType = layoutType.opposite() }
                    )*/
                }

            }
            itemsIndexed(data) { index, item ->
                WorkoutTemplateCard(
                    modifier = Modifier.height(130.dp),
                    cardShape = LiftingTheme.shapes.gridShapes(
                        listSize = data.size,
                        index = index
                    ),
                    title = item.workout.name.orEmpty().ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) },
                    description = item.workout.note,
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
}

internal enum class LayoutType {
    List, Grid
}

internal fun LayoutType.opposite(): LayoutType {
    return when (this) {
        LayoutType.List -> LayoutType.Grid
        LayoutType.Grid -> LayoutType.List
    }
}