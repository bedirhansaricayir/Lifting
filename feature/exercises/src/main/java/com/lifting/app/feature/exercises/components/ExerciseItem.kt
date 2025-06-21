package com.lifting.app.feature.exercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R

/**
 * Created by bedirhansaricayir on 01.08.2024
 */

@Composable
internal fun ExerciseItem(
    modifier: Modifier = Modifier,
    exerciseImage: Int?,
    exerciseName: String,
    exerciseType: String,
    exerciseLogCount: Long,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = LiftingTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            val exerciseNameWords = exerciseName.split(" ").filter { it.toCharArray()[0].isLetter() }
            val exerciseNameByFirstLetters = exerciseNameWords.fold("") { acc, word -> acc + word.take(1).uppercase() }

            exerciseImage?.let {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = exerciseImage),
                    contentDescription = stringResource(id = R.string.exercises_image_content_description)
                )
            } ?: Text(
                text = exerciseNameByFirstLetters,
                color = LiftingTheme.colors.onBackground
            )

        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = exerciseName,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground
            )
            Text(
                text = exerciseType,
                style = LiftingTheme.typography.subtitle2,
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.7f)
            )
        }
        if (exerciseLogCount > 0) {
            Text(
                text = exerciseLogCount.toString(),
                style = LiftingTheme.typography.subtitle2,
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}