package com.lifting.app.feature_home.presentation.tracker.components

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.theme.Black40


enum class TimeRange {
     SEVEN_DAYS, THIRTY_DAYS, SIXTY_DAYS, NINETY_DAYS, ONE_YEAR;
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
            color = if (isSelected) Black40 else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall
        )
    }
}
