package com.lifting.app.feature_tracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R


enum class TimeRange(val minusDay: Long) {
     SEVEN_DAYS(7), THIRTY_DAYS(30), SIXTY_DAYS(60), NINETY_DAYS(90), ONE_YEAR(365);
}

@Composable
fun TimeRangePicker(
    modifier: Modifier = Modifier,
    selectedTimeRange: TimeRange = TimeRange.SEVEN_DAYS,
    onTimeRangeSelected: (TimeRange) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        TimeRangeChip(
            time = stringResource(id = R.string.short_seven_days),
            isSelected = selectedTimeRange == TimeRange.SEVEN_DAYS
        ) {
            onTimeRangeSelected(TimeRange.SEVEN_DAYS)
        }

        TimeRangeChip(
            time = stringResource(id = R.string.short_thirty_days),
            isSelected = selectedTimeRange == TimeRange.THIRTY_DAYS
        ) {
            onTimeRangeSelected(TimeRange.THIRTY_DAYS)
        }

        TimeRangeChip(
            time = stringResource(id = R.string.short_sixty_days),
            isSelected = selectedTimeRange == TimeRange.SIXTY_DAYS
        ) {
            onTimeRangeSelected(TimeRange.SIXTY_DAYS)
        }

        TimeRangeChip(
            time = stringResource(id = R.string.short_ninety_days),
            isSelected = selectedTimeRange == TimeRange.NINETY_DAYS
        ) {
            onTimeRangeSelected(TimeRange.NINETY_DAYS)
        }

        TimeRangeChip(
            time = stringResource(id = R.string.short_one_year),
            isSelected = selectedTimeRange == TimeRange.ONE_YEAR
        ) {
            onTimeRangeSelected(TimeRange.ONE_YEAR)
        }
    }
}

@Composable
private fun TimeRangeChip(
    time: String,
    isSelected: Boolean,
    onTimeRangeSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .requiredWidth(50.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onTimeRangeSelected()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall
        )
    }
}
