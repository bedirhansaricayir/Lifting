package com.lifting.app.feature.exercise_detail

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.LogEntriesWithWorkout
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.LiftingCard
import com.lifting.app.core.ui.components.LiftingTabIndicator2
import com.lifting.app.core.ui.components.SessionExerciseCardItem
import com.lifting.app.core.ui.components.chart_redesig.BarProperties
import com.lifting.app.core.ui.components.chart_redesig.Bars
import com.lifting.app.core.ui.components.chart_redesig.ColumnChart
import com.lifting.app.core.ui.components.chart_redesig.DrawStyle
import com.lifting.app.core.ui.components.chart_redesig.HorizontalIndicatorProperties
import com.lifting.app.core.ui.components.chart_redesig.LabelProperties
import com.lifting.app.core.ui.components.chart_redesig.Line
import com.lifting.app.core.ui.components.chart_redesig.line.LineChart
import com.lifting.app.core.ui.extensions.getExerciseNoteString
import com.lifting.app.core.ui.extensions.getReadableName
import com.lifting.app.core.ui.extensions.toLocalizedMuscleName
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.exercise_detail.components.ChartFilterDropdown
import kotlinx.coroutines.launch
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@Composable
internal fun ExerciseDetailScreen(
    state: ExerciseDetailUIState,
    onEvent: (ExerciseDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    ExerciseDetailContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun ExerciseDetailContent(
    state: ExerciseDetailUIState,
    onEvent: (ExerciseDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabData = listOf(
        stringResource(id = com.lifting.app.core.ui.R.string.statistics),
        stringResource(id = com.lifting.app.core.ui.R.string.history),
        stringResource(id = com.lifting.app.core.ui.R.string.about)
    )
    val pagerState = rememberPagerState(pageCount = { tabData.size })
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    CollapsingToolBarScaffold(
        modifier = modifier.fillMaxSize(),
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            LiftingTopBar(
                title = state.exercise?.name.orEmpty(),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                navigationIcon = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.back,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onEvent(ExerciseDetailUIEvent.OnBackClicked) }
                    )
                },
                layout = {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = Color.Transparent,
                        contentColor = LiftingTheme.colors.onBackground,
                        divider = {
                            HorizontalDivider(thickness = LiftingTheme.dimensions.default)
                        },
                        indicator = { tabPositions ->
                            LiftingTabIndicator2(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            )
                        }
                    ) {
                        tabData.forEachIndexed { index, text ->
                            Tab(
                                selectedContentColor = LiftingTheme.colors.primary,
                                unselectedContentColor = LiftingTheme.colors.onBackground.copy(0.5f),
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        text = text,
                                        style = LiftingTheme.typography.subtitle1
                                    )
                                }
                            )
                        }
                    }
                }
            )
        },
        body = {
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    STATISTICS -> {
                        StatisticsTabPage(state.exercise, onEvent, state.chartData, state.periods, state.isDropdownVisible)
                    }

                    HISTORY -> {
                        HistoryTabPage(state.logEntries, state.exercise)
                    }

                    ABOUT -> {
                        AboutTabPage(state.exercise)
                    }
                }
            }
        }
    )
}


@Composable
private fun StatisticsTabPage(
    exercise: Exercise?,
    onEvent: (ExerciseDetailUIEvent) -> Unit,
    data: Map<ChartType, Pair<ChartPeriod, List<ChartData>>>,
    periods: Map<ChartType, List<Selectable<ChartPeriod>>>,
    isDropdownVisible: Map<ChartType, Boolean>,
    modifier: Modifier = Modifier
) {
    val lineColor = LiftingTheme.colors.primary

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(LiftingTheme.dimensions.large)
    ) {
        items(data.toList(), key = { it.first.name }) { (chartType, chartData) ->
            Surface(
                modifier = Modifier.padding(bottom = LiftingTheme.dimensions.large),
                color = LiftingTheme.colors.surface,
                shape = LiftingTheme.shapes.medium
            ) {
                Column(Modifier.padding(LiftingTheme.dimensions.large)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chartType.title.asString(),
                            style = LiftingTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                            color = LiftingTheme.colors.primary
                        )

                        ChartFilterDropdown(
                            selectedText = chartData.first.title.asString(),
                            isDropdownVisible = isDropdownVisible[chartType] == true,
                            periods = periods[chartType].orEmpty(),
                            onClick = { onEvent(ExerciseDetailUIEvent.OnPeriodMenuClick(chartType)) },
                            onItemSelected = { onEvent(ExerciseDetailUIEvent.OnPeriodItemSelected(chartType,it)) },
                            onDismiss = { onEvent(ExerciseDetailUIEvent.OnDropdownDismissed(chartType)) }
                        )
                    }


                    val lineData = remember(chartData.second) {
                        derivedStateOf {
                            Line(
                                values = chartData.second.map { it.value },
                                color = SolidColor(lineColor),
                                firstGradientFillColor = lineColor.copy(alpha = .5f),
                                secondGradientFillColor = Color.Transparent,
                                strokeAnimationSpec = tween(
                                    2000,
                                    easing = EaseInOutCubic
                                ),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                            )
                        }
                    }

                    val barData = remember(chartData.second) {
                        derivedStateOf {
                            chartData.second.map { data ->
                                Bars(
                                    label = data.label,
                                    values = listOf(
                                        Bars.Data(
                                            value = data.value,
                                            color = SolidColor(lineColor)
                                        )
                                    )
                                )
                            }
                        }
                    }

                    if (chartType == ChartType.HEAVIEST_WEIGHT) {
                        LineChart(
                            modifier = Modifier.height(250.dp).fillMaxWidth(),
                            data = listOf(lineData.value),
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = LiftingTheme.typography.caption,
                                textColor = LiftingTheme.colors.onBackground,
                                padding = 16.dp
                            ),
                            labelProperties = LabelProperties(
                                labels = chartData.second.map { it.label },
                                textStyle = LiftingTheme.typography.caption,
                                textColor = LiftingTheme.colors.onBackground,
                                enabled = true
                            )
                        )
                    } else {
                        ColumnChart(
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth(),
                            barProperties = BarProperties(
                                thickness = 15.dp,
                                spacing = 3.dp,
                                cornerRadius = Bars.Data.Radius.Circular(6.dp),
                                style = DrawStyle.Fill
                            ),
                            onBarClick = {
                                Log.d("onBarClick", "$it")
                            },
                            onBarLongClick = {
                                Log.d("onBarLongClick", "$it")
                            },
                            indicatorProperties = HorizontalIndicatorProperties(
                                textStyle = LiftingTheme.typography.caption,
                                textColor = LiftingTheme.colors.onBackground,
                                padding = 16.dp
                            ),
                            labelProperties = LabelProperties(
                                textStyle = LiftingTheme.typography.caption,
                                textColor = LiftingTheme.colors.onBackground,
                                enabled = true
                            ),
                            data = barData.value
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTabPage(
    data: List<LogEntriesWithWorkout>,
    exercise: Exercise?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(LiftingTheme.dimensions.large)
    ) {
        items(
            data,
            key = { it.junction.id }
        ) { item ->
            SessionExerciseCardItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                onClick = { },
                title = item.workout.name,
                subtitle = (item.workout.startAt
                    ?: item.workout.createdAt)?.format(
                    DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM,
                        FormatStyle.SHORT
                    )
                ),
                exerciseCategory = exercise?.category,
                entries = item.logEntries,
                notes = item.notes,
            )
        }
    }
}

@Composable
private fun AboutTabPage(
    exercise: Exercise?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(LiftingTheme.dimensions.large),
    ) {
        item(
            key = exercise?.exerciseId
        ) {
            exercise?.let { exercise ->
                AboutTabItem(
                    title = stringResource(com.lifting.app.core.ui.R.string.notes),
                    value = if (exercise.notes.isNullOrBlank().not()) exercise.notes!! else getExerciseNoteString(exercise.exerciseId),
                    expandable = true
                )

                if (exercise.primaryMuscleTag != null) {
                    AboutTabItem(
                        title = stringResource(com.lifting.app.core.ui.R.string.primary_muscle),
                        value = stringResource(exercise.primaryMuscleTag!!.toLocalizedMuscleName())
                    )
                }

                AboutTabItem(
                    title = stringResource(com.lifting.app.core.ui.R.string.category),
                    value = stringResource(exercise.category.getReadableName())
                )
            }
        }
    }
}

@Composable
private fun AboutTabItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    expandable: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(false) }
    LiftingCard(
        onClick = {
            if (expandable) {
                isExpanded = !isExpanded
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = LiftingTheme.dimensions.small)
    ) {
        Column(
            modifier = Modifier.padding(LiftingTheme.dimensions.large),
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
        ) {
            Text(
                text = title,
                style = LiftingTheme.typography.subtitle2,
                color = LiftingTheme.colors.onBackground.copy(0.75f)
            )


            Text(
                text = value,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground,
                maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

private const val STATISTICS = 0
private const val HISTORY = 1
private const val ABOUT = 2