package com.lifting.app.feature.workout.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 05.02.2025
 */

@Composable
internal fun WorkoutTemplateSectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .padding(LiftingTheme.dimensions.small),
        text = text,
        style = LiftingTheme.typography.subtitle2,
        color = LiftingTheme.colors.onBackground.copy(0.75f)
    )
}