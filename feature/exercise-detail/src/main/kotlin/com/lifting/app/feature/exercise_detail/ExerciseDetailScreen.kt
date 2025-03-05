package com.lifting.app.feature.exercise_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.components.LiftingIconButton
import com.lifting.app.core.ui.components.LiftingTabIndicator
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import kotlinx.coroutines.launch
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

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
internal fun ExerciseDetailContent(
    state: ExerciseDetailUIState,
    onEvent: (ExerciseDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        ExerciseDetailUIState.Error -> {}
        ExerciseDetailUIState.Loading -> {}
        is ExerciseDetailUIState.Success ->
            ExerciseDetailContentSuccess(
                state = state,
                onEvent = onEvent,
                modifier = modifier
            )
    }
}

@Composable
internal fun ExerciseDetailContentSuccess(
    state: ExerciseDetailUIState.Success,
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
        modifier = Modifier.fillMaxSize(),
        state = scaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            LiftingTopBar(
                title = "Exercise Name",
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                navigationIcon = {
                    LiftingIconButton(
                        imageVector = LiftingTheme.icons.back,
                        contentDescription = String.EMPTY,
                        tint = LiftingTheme.colors.onBackground,
                        onClick = {}
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
                            LiftingTabIndicator(
                                tabPosition = tabPositions,
                                pagerState = pagerState,
                                indicatorColor = LiftingTheme.colors.primary
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
                                    Text(text = text)
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
            ) {
                when (it) {
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(LiftingTheme.dimensions.large)
                        ) {
                            items(50) {
                                Text(text = "text $it")
                            }
                        }
                    }

                    1 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(LiftingTheme.dimensions.large)
                        ) {
                            items(50) {
                                Text(text = "text $it")
                            }
                        }

                    }

                    2 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(LiftingTheme.dimensions.large)
                        ) {
                            items(50) {
                                Text(text = "text $it")
                            }
                        }
                    }
                }
            }
        }
    )

}