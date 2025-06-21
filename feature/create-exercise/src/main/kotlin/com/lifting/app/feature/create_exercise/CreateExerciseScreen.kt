package com.lifting.app.feature.create_exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingTextField
import com.lifting.app.core.ui.components.SingleSelectableCard
import com.lifting.app.core.ui.extensions.getReadableName
import com.lifting.app.core.ui.extensions.toLocalizedMuscleName
import com.lifting.app.core.ui.top_bar.LiftingBottomSheetTopBar

/**
 * Created by bedirhansaricayir on 13.08.2024
 */

@Composable
internal fun CreateExerciseScreen(
    modifier: Modifier = Modifier,
    state: CreateExerciseUIState,
    onEvent: (CreateExerciseUIEvent) -> Unit,
) {
    CreateExerciseScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CreateExerciseScreenContent(
    modifier: Modifier = Modifier,
    state: CreateExerciseUIState,
    onEvent: (CreateExerciseUIEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background)
    ) {
        LiftingBottomSheetTopBar(
            isEnabledActionButton = state.exerciseName?.isNotEmpty() == true,
            onNavigationClick = { onEvent(CreateExerciseUIEvent.OnNavigationClick) },
            onActionClick = { onEvent(CreateExerciseUIEvent.OnActionClick) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LiftingTheme.dimensions.large)
                .imePadding()
                .imeNestedScroll(),
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
        ) {
            Text(
                text = stringResource(com.lifting.app.core.ui.R.string.name),
                style = LiftingTheme.typography.caption,
                color = LiftingTheme.colors.onBackground
            )

            LiftingTextField(
                value = state.exerciseName.orEmpty(),
                onValueChange = { onEvent(CreateExerciseUIEvent.OnExerciseNameChanged(it)) },
                keyboardOptions = KeyboardOptions().copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                placeholder = stringResource(com.lifting.app.core.ui.R.string.exercise_name)
            )
            Text(
                text = stringResource(com.lifting.app.core.ui.R.string.notes),
                style = LiftingTheme.typography.caption,
                color = LiftingTheme.colors.onBackground
            )

            LiftingTextField(
                value = state.exerciseNotes.orEmpty(),
                onValueChange = { onEvent(CreateExerciseUIEvent.OnExerciseNotesChanged(it)) },
                keyboardOptions = KeyboardOptions().copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                placeholder = stringResource(com.lifting.app.core.ui.R.string.exercise_notes)
            )

            SingleSelectableCard(
                modifier = Modifier.padding(top = LiftingTheme.dimensions.small),
                name = stringResource(com.lifting.app.core.ui.R.string.category),
                value = stringResource(state.selectedCategory.getReadableName()),
                onClick = { onEvent(CreateExerciseUIEvent.OnCategoryClicked(state.selectedCategory.tag)) }
            )

            SingleSelectableCard(
                modifier = Modifier.padding(top = LiftingTheme.dimensions.small),
                name = stringResource(com.lifting.app.core.ui.R.string.primary_muscle),
                value = state.selectedMuscle?.let {
                    stringResource(it.tag.toLocalizedMuscleName())
                },
                onClick = { onEvent(CreateExerciseUIEvent.OnMuscleClicked(state.selectedMuscle?.tag)) }
            )
        }
    }
}