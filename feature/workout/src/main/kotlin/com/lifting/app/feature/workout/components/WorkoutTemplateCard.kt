package com.lifting.app.feature.workout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingCard

/**
 * Created by bedirhansaricayir on 02.02.2025
 */

@Composable
internal fun WorkoutTemplateCard(
    title: String,
    exercisesInfo: String,
    modifier: Modifier = Modifier,
    shape: Shape = LiftingTheme.shapes.medium,
    description: String? = String.EMPTY,
    onClick: () -> Unit = {},
) {
    LiftingCard(
        modifier = modifier,
        shape = shape,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LiftingTheme.dimensions.large),
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = LiftingTheme.typography.subtitle1,
                    color = LiftingTheme.colors.onBackground
                )
            }

            description?.let {
                Text(
                    text = it,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = LiftingTheme.typography.subtitle2,
                    color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f)
                )
            }

            Text(
                text = exercisesInfo,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = LiftingTheme.typography.subtitle2,
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}

internal val liftingTemplates = listOf(
    WorkoutTemplateData(
        title = "Workout Template 1",
        exerciseList = listOf(
            "3 x Bench Press",
            "3 x Squat",
            "3 x Bicep Curl"
        )
    ),
    WorkoutTemplateData(
        title = "Workout Template 2",
        exerciseList = listOf(
            "3 x Deneme Tahtası Bla bla bla",
            "3 x Snatch With From The Floor",
            "3 x Clean&Jerk 2+1x3@70KG",
            "5 x Snatch Deadlift With From The Floor",
            "3 x Weighted Pull Up"
        )
    ),
    WorkoutTemplateData(
        title = "Workout Template 1",
        exerciseList = listOf(
            "3 x Bench Press",
            "3 x Squat",
            "3 x Bicep Curl"
        )
    ),
    WorkoutTemplateData(
        title = "Workout Template 2",
        exerciseList = listOf(
            "3 x Deneme Tahtası Bla bla bla",
            "3 x Snatch With From The Floor",
            "3 x Clean&Jerk 2+1x3@70KG",
            "5 x Snatch Deadlift With From The Floor",
            "3 x Weighted Pull Up"
        )
    ),
    WorkoutTemplateData(
        title = "Workout Template 1",
        exerciseList = listOf(
            "3 x Bench Press",
            "3 x Squat",
            "3 x Bicep Curl"
        )
    ),
    WorkoutTemplateData(
        title = "Workout Template 2",
        exerciseList = listOf(
            "3 x Deneme Tahtası Bla bla bla",
            "3 x Snatch With From The Floor",
            "3 x Clean&Jerk 2+1x3@70KG",
            "5 x Snatch Deadlift With From The Floor",
            "3 x Weighted Pull Up"
        )
    )
)

data class WorkoutTemplateData(
    val title: String,
    val description: String = "This program help to your develop of leg strength and work capacity",
    val exerciseList: List<String>
)