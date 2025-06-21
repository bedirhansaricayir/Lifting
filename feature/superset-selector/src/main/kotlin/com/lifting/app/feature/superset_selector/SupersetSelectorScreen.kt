package com.lifting.app.feature.superset_selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.feature.superset_selector.components.SupersetExerciseListItem

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
@Composable
internal fun SupersetSelectorScreen(
    state: SupersetSelectorUIState,
    onEvent: (SupersetSelectorUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    SupersetSelectorScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun SupersetSelectorScreenContent(
    state: SupersetSelectorUIState,
    onEvent: (SupersetSelectorUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background)
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                text = stringResource(com.lifting.app.core.ui.R.string.superset),
                textAlign = TextAlign.Center,
                style = LiftingTheme.typography.header2,
                color = LiftingTheme.colors.onBackground
            )
        }

        items(state.junctions, key = { it.junction.id }) {
            SupersetExerciseListItem(
                supersetId = it.junction.supersetId,
                exerciseName = it.exercise.name
                    ?: stringResource(id = com.lifting.app.core.ui.R.string.add_set),
                isSelectedJunction = it.junction.id == state.junctionId,
                onClick = {
                    onEvent(SupersetSelectorUIEvent.OnExerciseClicked(it.junction))
                }
            )
        }
    }
}