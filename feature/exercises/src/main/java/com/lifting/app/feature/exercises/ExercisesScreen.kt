package com.lifting.app.feature.exercises

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.core.ui.exercises.ExerciseItem
import com.lifting.app.core.ui.top_bar.LiftingTopSearchBar
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 30.07.2024
 */

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExercisesViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val exercisesUIState: ExercisesUIState by viewModel.state.collectAsStateWithLifecycle()
    val exercisesUIEffect = viewModel.effect
    val results by viewModel.exercises.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(exercisesUIEffect) {
        exercisesUIEffect.collect { effect ->
            when (effect) {
                ExercisesUIEffect.NavigateToAddExercise -> onAddClick()
            }
        }
    }

    ExercisesScreenContent(
        modifier = modifier,
        state = exercisesUIState,
        onEvent = viewModel::setEvent,
    )

}

@Composable
fun ExercisesScreenContent(
    modifier: Modifier = Modifier,
    state: ExercisesUIState,
    onEvent: (ExercisesUIEvent) -> Unit,
) {
    when (state) {
        ExercisesUIState.Loading -> {}

        is ExercisesUIState.Success -> ListScreen(state = state, onEvent = onEvent)

        is ExercisesUIState.Error -> {}
    }
}

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    state: ExercisesUIState.Success,
    onEvent: (ExercisesUIEvent) -> Unit
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    BackHandler(
        enabled = state.searchMode
    ) {
        onEvent(ExercisesUIEvent.OnBackClick)
    }

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = state.searchMode, label = ""
                )
                { isSearchMode ->
                    if (isSearchMode) {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                        ) {
                            LiftingTopSearchBar(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = state.searchQuery,
                                onBackClick = {
                                    onEvent(ExercisesUIEvent.OnBackClick)
                                },
                                onValueChange = {
                                    onEvent(ExercisesUIEvent.OnSearchQueryChanged(it))
                                },
                                leadingIconModifier = Modifier.sharedElement(
                                    state = rememberSharedContentState(key = SHARED_SEARCH_KEY),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            )
                        }
                    } else {
                        LiftingTopBar(
                            title = "Exercises",
                            toolbarState = scaffoldState.toolbarState,
                            toolbarScope = this@CollapsingToolBarScaffold,
                            actions = {
                                IconButton(
                                    onClick = { onEvent(ExercisesUIEvent.OnSearchClick) },
                                    content = {
                                        Icon(
                                            modifier = Modifier.sharedElement(
                                                state = rememberSharedContentState(key = SHARED_SEARCH_KEY),
                                                animatedVisibilityScope = this@AnimatedContent
                                            ),
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Button"
                                        )
                                    }
                                )
                                IconButton(
                                    onClick = { onEvent(ExercisesUIEvent.OnAddClick) },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add Button"
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        },
        body = {
            ExerciseList(
                exercisesWithHeader = state.groupedExercises.orEmpty(),
                onExerciseClick = {}
            )
        }
    )
}

@Composable
fun ExerciseList(
    modifier: Modifier = Modifier,
    exercisesWithHeader: Map<String, List<ExerciseWithInfo>>,
    onExerciseClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        exercisesWithHeader.map { entry ->
            stickyHeader {
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
                    exerciseType = exercise.exercise.primaryMuscleTag ?: "",
                    exerciseLogCount = exercise.logsCount,
                    onClick = onExerciseClick
                )
            }
        }
    }
}

private const val SHARED_SEARCH_KEY = "searchImage"