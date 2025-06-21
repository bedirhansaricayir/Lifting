package com.lifting.app.feature.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.clearFocus
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseWithInfo
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.LiftingSearchField
import com.lifting.app.core.ui.extensions.detectGesturesThenClearFocus
import com.lifting.app.core.ui.extensions.toLocalizedMuscleName
import com.lifting.app.core.ui.extensions.toLocalizedString
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.exercises.components.ExerciseFilterDropdown
import com.lifting.app.feature.exercises.components.ExerciseItem
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
private fun ExercisesScreenContent(
    state: ExercisesUIState,
    onEvent: (ExercisesUIEvent) -> Unit,
    isBottomSheet: Boolean,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    CollapsingToolBarScaffold(
        modifier = modifier
            .background(LiftingTheme.colors.background)
            .detectGesturesThenClearFocus(),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold,
                actions = {
                    LiftingButton(
                        buttonType = LiftingButtonType.IconButton(
                            icon = LiftingTheme.icons.add,
                            tint = LiftingTheme.colors.onBackground,
                        ),
                        onClick = {
                            onEvent(ExercisesUIEvent.OnAddClick)
                                .clearFocus(keyboard, focusManager)
                        }
                    )
                },
                layout = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LiftingTheme.dimensions.large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
                    ) {
                        LiftingSearchField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.searchQuery,
                            useAnimatedPlaceholder = state.useAnimatedPlaceholder,
                            onValueChange = {
                                onEvent(ExercisesUIEvent.OnSearchQueryChanged(it))
                            }
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ExerciseFilterDropdown(
                                modifier = Modifier.weight(1f),
                                selectedText = state.equipmentFilterTitle.asString(),
                                isDropdownVisible = state.selectedFilterType == ExercisesFilterType.EQUIPMENT,
                                selectableItems = state.equipmentFilterItems,
                                itemDisplayText = { UiText.StringResource(it.toLocalizedString()) },
                                onItemSelected = {
                                    onEvent(
                                        ExercisesUIEvent.OnEquipmentFilterClick(
                                            it.item
                                        )
                                    )
                                },
                                onClick = {
                                    onEvent(ExercisesUIEvent.OnEquipmentChipClick).clearFocus(
                                        keyboard,
                                        focusManager
                                    )
                                },
                                onDismiss = { onEvent(ExercisesUIEvent.OnDismissEquipmentDropDown) },
                                onClear = { onEvent(ExercisesUIEvent.OnRemoveFiltersClick(ExercisesFilterType.EQUIPMENT)) }
                            )
                            ExerciseFilterDropdown(
                                modifier = Modifier.weight(1f),
                                selectedText = state.categoryFilterTitle.asString(),
                                isDropdownVisible = state.selectedFilterType == ExercisesFilterType.CATEGORY,
                                selectableItems = state.categoryFilterItems,
                                itemDisplayText = { UiText.StringResource(it.toLocalizedMuscleName()) },
                                onItemSelected = { onEvent(ExercisesUIEvent.OnCategoryFilterClick(it.item)) },
                                onClick = {
                                    onEvent(ExercisesUIEvent.OnCategoryChipClick).clearFocus(
                                        keyboard,
                                        focusManager
                                    )
                                },
                                onDismiss = { onEvent(ExercisesUIEvent.OnDismissCategoryDropDown) },
                                onClear = { onEvent(ExercisesUIEvent.OnRemoveFiltersClick(ExercisesFilterType.CATEGORY)) }
                            )
                        }
                    }

                }
            )
        },
        body = {
            ExerciseList(
                exercisesWithHeader = state.groupedExercises,
                onExerciseClick = { onEvent(ExercisesUIEvent.OnExerciseClick(it, isBottomSheet)) }
            )
        }
    )
}

@Composable
internal fun ExerciseList(
    modifier: Modifier = Modifier,
    exercisesWithHeader: Map<String, List<ExerciseWithInfo>>,
    onExerciseClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LiftingTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    exerciseType = exercise.exercise.primaryMuscleTag?.let {
                        stringResource(it.toLocalizedMuscleName())
                    } ?: "",
                    exerciseLogCount = exercise.logsCount,
                    onClick = { onExerciseClick(exercise.exercise.exerciseId) }
                )
            }
        }
    }
}