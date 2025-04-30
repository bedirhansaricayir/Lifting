package com.lifting.app.feature.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.common.extensions.isSameYear
import com.lifting.app.core.common.extensions.toDuration
import com.lifting.app.core.common.extensions.toEpochMillis
import com.lifting.app.core.common.extensions.toKg
import com.lifting.app.core.common.extensions.toLocalDate
import com.lifting.app.core.common.extensions.toLocalDateTime
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.extensions.lighterColor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by bedirhansaricayir on 08.03.2025
 */


@Composable
internal fun HistoryListItem(
    title: String,
    date: LocalDateTime?,
    totalExercises: Int,
    duration: Long?,
    volume: Double?,
    prs: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = DateTimeFormatter.ofPattern(if (date.isSameYear()) "EEE, MMM d" else "MMM d, yyyy")

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = LiftingTheme.colors.background.lighterColor()
        ),
        shape = LiftingTheme.shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LiftingTheme.dimensions.large),
            verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
        ){
            Text(
                text = title,
                style = LiftingTheme.typography.subtitle1,
                color = LiftingTheme.colors.onBackground
            )

            date?.let {
                Text(
                    text = dateFormatter.format(it.toEpochMillis().toLocalDate()),
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
                )
            }

            Text(
                text = pluralStringResource(
                    id = com.lifting.app.core.ui.R.plurals.number_of_exercises,
                    count = totalExercises,
                    totalExercises
                ),
                style = LiftingTheme.typography.caption,
                color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
            )

            SessionQuickInfo(duration = duration, volume = volume, prs = prs)

        }
    }
}

@Composable
private fun SessionQuickInfo(
    duration: Long?,
    volume: Double?,
    prs: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
    ){
        duration?.let {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.timer,
                text = duration.toDuration()
            )
        }
        volume?.let {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.weight,
                text = "${volume.toKg()} kg"
            )
        }
        if (prs > 0) {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.trophy,
                text = pluralStringResource(
                    id = com.lifting.app.core.ui.R.plurals.number_of_prs,
                    count = prs,
                    prs
                )
            )
        }
    }
}

@Composable
private fun SessionQuickInfoItem(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
    ){
        Icon(
            modifier = Modifier.size(LiftingTheme.dimensions.large),
            painter = icon,
            contentDescription = String.EMPTY,
            tint = LiftingTheme.colors.onBackground.copy(0.75f)
        )
        Text(
            text = text,
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(0.75f)
        )
    }
}