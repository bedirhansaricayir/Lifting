package com.lifting.app.feature.exercises_category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.ui.extensions.getExamplesString
import com.lifting.app.core.ui.extensions.getReadableName

/**
 * Created by bedirhansaricayir on 22.08.2024
 */

@Composable
internal fun ExerciseCategoryScreen(
    modifier: Modifier = Modifier,
    state: ExercisesCategoryUIState,
    onEvent: (ExercisesCategoryUIEvent) -> Unit
) {
    ExerciseCategoryScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent
    )
}

@Composable
private fun ExerciseCategoryScreenContent(
    state: ExercisesCategoryUIState,
    onEvent: (ExercisesCategoryUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background),
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                text = stringResource(com.lifting.app.core.ui.R.string.select_category),
                textAlign = TextAlign.Center,
                style = LiftingTheme.typography.header2,
                color = LiftingTheme.colors.onBackground
            )
        }
        itemsIndexed(
            state.categories,
            key = { _: Int, category: ExerciseCategory -> category.tag }
        ) { index, category ->
            ExerciseCategoryItem(
                exerciseCategory = category,
                isSelected = category.tag == state.selectedCategory,
                onClick = { onEvent(ExercisesCategoryUIEvent.OnCategoryClick(category)) }
            )
        }
    }
}

@Composable
private fun ExerciseCategoryItem(
    modifier: Modifier = Modifier,
    exerciseCategory: ExerciseCategory,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(exerciseCategory.getReadableName()),
                color = LiftingTheme.colors.onBackground,
                style = LiftingTheme.typography.subtitle1
            )
            Text(
                text = stringResource(exerciseCategory.getExamplesString()),
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
                style = LiftingTheme.typography.caption
            )
        }
        if (isSelected) {
            Icon(
                imageVector = LiftingTheme.icons.done,
                contentDescription = String.EMPTY,
                tint = LiftingTheme.colors.primary
            )
        }

    }
}