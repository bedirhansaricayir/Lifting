package com.lifting.app.feature.superset_selector.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.extensions.darkerOrLighter
import com.lifting.app.core.ui.extensions.randomColorById

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Composable
internal fun SupersetExerciseListItem(
    modifier: Modifier = Modifier,
    supersetId: Int?,
    exerciseName: String,
    isSelectedJunction: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .run {
                    if (isSelectedJunction || supersetId != null) {
                        this
                    } else {
                        clickable(onClick = onClick)
                    }
                }
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = exerciseName,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
            )

            supersetId?.let {
                Spacer(Modifier.width(LiftingTheme.dimensions.large))
                val supersetColor = Color.randomColorById(supersetId)
                Box(
                    modifier = Modifier
                        .background(supersetColor.copy(.5f), CircleShape)
                        .padding(LiftingTheme.dimensions.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(com.lifting.app.core.ui.R.string.superset),
                        style = LiftingTheme.typography.button,
                        color = supersetColor.darkerOrLighter(0.8f)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            if (isSelectedJunction) {
                Box(
                    modifier = Modifier
                        .background(
                            color = LiftingTheme.colors.primary,
                            shape = CircleShape
                        )
                        .padding(LiftingTheme.dimensions.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = com.lifting.app.core.ui.R.string.superset_selected_exercise),
                        style = LiftingTheme.typography.button.copy(fontSize = 12.sp),
                        color = LiftingTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}