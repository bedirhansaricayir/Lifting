package com.lifting.app.feature.exercises_muscle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.lifting.app.core.model.Muscle

/**
 * Created by bedirhansaricayir on 28.08.2024
 */

@Composable
internal fun ExercisesMuscleScreen(
    modifier: Modifier = Modifier,
    state: ExercisesMuscleUIState,
    onEvent: (ExercisesMuscleUIEvent) -> Unit
) {
    ExercisesMuscleScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent
    )
}

@Composable
internal fun ExercisesMuscleScreenContent(
    modifier: Modifier = Modifier,
    state: ExercisesMuscleUIState,
    onEvent: (ExercisesMuscleUIEvent) -> Unit
) {
    when (state) {
        is ExercisesMuscleUIState.Error -> {}
        ExercisesMuscleUIState.Loading -> {}
        is ExercisesMuscleUIState.Success ->
            ExercisesMuscleListScreen(
                modifier = modifier,
                state = state,
                onEvent = onEvent
            )
    }
}

@Composable
internal fun ExercisesMuscleListScreen(
    modifier: Modifier = Modifier,
    state: ExercisesMuscleUIState.Success,
    onEvent: (ExercisesMuscleUIEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background),
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                text = stringResource(com.lifting.app.core.ui.R.string.select_muscle),
                textAlign = TextAlign.Center,
                style = LiftingTheme.typography.header2,
                color = LiftingTheme.colors.onBackground
            )
        }
        items(
            state.muscles,
            key = { muscle -> muscle.tag }
        ) { muscle ->
            ExercisesMuscleListItem(
                muscle = muscle,
                isSelected = muscle.tag == state.selectedMuscle,
                onClick = { onEvent(ExercisesMuscleUIEvent.OnMuscleClick(muscle)) }
            )
        }
    }
}

@Composable
internal fun ExercisesMuscleListItem(
    modifier: Modifier = Modifier,
    muscle: Muscle,
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
        Text(
            text = muscle.name,
            color = LiftingTheme.colors.onBackground,
            style = LiftingTheme.typography.subtitle1
        )

        if (isSelected) {
            Icon(
                imageVector = LiftingTheme.icons.done,
                contentDescription = String.EMPTY,
                tint = LiftingTheme.colors.primary
            )
        }

    }
}