package com.lifting.app.feature.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import com.lifting.app.core.common.extensions.isSameYear
import com.lifting.app.core.designsystem.LiftingTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by bedirhansaricayir on 25.03.2025
 */

@Composable
internal fun HistoryHeader(
    date: LocalDate,
    totalWorkout: Int,
    modifier: Modifier = Modifier
) {
    val dateFormatter = DateTimeFormatter.ofPattern(if (date.isSameYear()) "MMMM" else "MMMM yyyy")
    HistoryHeader(
        modifier = modifier,
        title = dateFormatter.format(date),
        totalWorkouts = totalWorkout
    )
}

@Composable
internal fun HistoryHeader(
    totalWorkouts: Int,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        title?.let {
            Text(
                text = it,
                style = LiftingTheme.typography.subtitle1.copy(color = LiftingTheme.colors.onBackground)
            )

        }
        Text(
            text = pluralStringResource(
                id = com.lifting.app.core.ui.R.plurals.number_of_workouts,
                totalWorkouts,
                totalWorkouts
            ),
            style = LiftingTheme.typography.subtitle2.copy(
                color = LiftingTheme.colors.onBackground.copy(
                    0.75f
                )
            )
        )
    }
}