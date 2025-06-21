package com.lifting.app.feature.workout.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.LayoutType
import com.lifting.app.core.model.ScrollDirection
import com.lifting.app.core.model.TemplateWithWorkout
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.extensions.scroll_direction.observeScrollDirection

/**
 * Created by bedirhansaricayir on 27.04.2025
 */

@Composable
internal fun TransitionableLazyList(
    data: List<TemplateWithWorkout>,
    layoutType: LayoutType,
    onCreateTemplateClicked: () -> Unit,
    onTemplateClicked: (String) -> Unit,
    onScrollDirectionChanged: (ScrollDirection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val columnState = rememberLazyListState()

    gridState.observeScrollDirection { onScrollDirectionChanged(it) }
    columnState.observeScrollDirection { onScrollDirectionChanged(it) }

    AnimatedContent(
        targetState = layoutType,
    ) { layout ->
        when (layout) {
            LayoutType.List -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .background(LiftingTheme.colors.background),
                    state = columnState,
                    contentPadding = PaddingValues(horizontal = LiftingTheme.dimensions.large),
                    verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
                    horizontalAlignment = Alignment.Start
                ) {
                    item(
                        key = "list_templates_section"
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

                            LiftingButton(
                                buttonType = LiftingButtonType.IconButton(
                                    icon = LiftingTheme.icons.add,
                                    tint = LiftingTheme.colors.onBackground
                                ),
                                onClick = onCreateTemplateClicked
                            )
                        }
                    }

                    itemsIndexed(
                        data,
                        key = { index, item -> "list ${item.template.id}" }
                    ) { index, item ->
                        WorkoutTemplateCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = item.workout.name.orEmpty()
                                .ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) },
                            description = item.workout.note,
                            exercisesInfo = stringResource(
                                id = com.lifting.app.core.ui.R.string.exercises_count,
                                item.junctions.size
                            ),
                            onClick = { onTemplateClicked(item.template.id) }
                        )
                    }

                    item(key = "list_lifting_templates_section") {
                        WorkoutTemplateSectionHeader(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.lifting_templates)
                        )
                    }

                    itemsIndexed(
                        liftingTemplates
                    ) { index, item ->
                        WorkoutTemplateCard(
                            modifier = Modifier.fillMaxWidth(),
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

            LayoutType.Grid -> {
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize()
                        .background(LiftingTheme.colors.background),
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = LiftingTheme.dimensions.large),
                    verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
                    horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
                ) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        key = "grid_templates_section"
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

                            LiftingButton(
                                buttonType = LiftingButtonType.IconButton(
                                    icon = LiftingTheme.icons.add,
                                    tint = LiftingTheme.colors.onBackground
                                ),
                                onClick = onCreateTemplateClicked
                            )
                        }

                    }
                    itemsIndexed(
                        data,
                        key = { index, item -> "grid ${item.template.id}" }
                    ) { index, item ->
                        WorkoutTemplateCard(
                            modifier = Modifier.height(130.dp),
                            title = item.workout.name.orEmpty()
                                .ifBlank { stringResource(id = com.lifting.app.core.ui.R.string.unnamed_template) },
                            description = item.workout.note,
                            exercisesInfo = stringResource(
                                id = com.lifting.app.core.ui.R.string.exercises_count,
                                item.junctions.size
                            ),
                            onClick = { onTemplateClicked(item.template.id) }
                        )
                    }

                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        key = "grid_lifting_templates_section"
                    ) {
                        WorkoutTemplateSectionHeader(
                            text = stringResource(id = com.lifting.app.core.ui.R.string.lifting_templates)
                        )
                    }

                    itemsIndexed(
                        liftingTemplates
                    ) { index, item ->
                        WorkoutTemplateCard(
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
    }
}