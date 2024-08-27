package com.lifting.app.feature.create_exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingTextField
import com.lifting.app.core.ui.components.SingleSelectableCard
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

@Composable
internal fun CreateExerciseScreenContent(
    modifier: Modifier = Modifier,
    state: CreateExerciseUIState,
    onEvent: (CreateExerciseUIEvent) -> Unit
) {
    when (state) {
        is CreateExerciseUIState.Loading -> LoadingScreen()

        is CreateExerciseUIState.Success -> CreateExerciseScreenSuccess(
            modifier = modifier,
            state = state,
            onEvent = onEvent,
        )

        is CreateExerciseUIState.Error -> ErrorScreen(message = state.message)
    }
}

@Composable
internal fun CreateExerciseScreenSuccess(
    modifier: Modifier = Modifier,
    state: CreateExerciseUIState.Success,
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Name", style = LiftingTheme.typography.caption, color = Color.Gray)

            LiftingTextField(
                value = state.exerciseName.orEmpty(),
                onValueChange = { onEvent(CreateExerciseUIEvent.OnExerciseNameChanged(it)) },
                keyboardOptions = KeyboardOptions().copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                placeholder = "Exercise Name"
            )
            Text(text = "Notes", style = LiftingTheme.typography.caption, color = Color.Gray)

            LiftingTextField(
                value = state.exerciseNotes.orEmpty(),
                onValueChange = { onEvent(CreateExerciseUIEvent.OnExerciseNotesChanged(it)) },
                keyboardOptions = KeyboardOptions().copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                placeholder = "Exercise Notes"
            )
            Spacer(modifier = Modifier.height(8.dp))

            SingleSelectableCard(
                name = "Category",
                value = state.selectedCategory.readableName,
                onClick = { onEvent(CreateExerciseUIEvent.OnCategoryClicked(state.selectedCategory.tag)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            SingleSelectableCard(
                name = "Primary Muscle",
                value = state.selectedMuscle,
                onClick = { }
            )
        }
    }
}

@Composable
internal fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Row(
         modifier = modifier
             .fillMaxWidth()
             .background(LiftingTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(text = "Loading..")
    }
}

@Composable
internal fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(LiftingTheme.colors.background)
    ) {
        Text(text = message ?: "Can't to add new exercise")
    }
}

