package com.lifting.app.feature_home.presentation.tracker.components.custom_calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_home.domain.model.CalendarModel
import com.lifting.app.theme.Black40
import com.lifting.app.theme.grey50
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    isValueAvailable: List<LocalDate>,
    onDateClickListener: (LocalDate) -> Unit
) {
    val dataSource = CalendarDataSource()
    var data by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    Column(modifier = modifier) {
        Header(
            data = data,
            onPrevClickListener = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                data = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = data.selectedDate.date
                )

            },
            onNextClickListener = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                data = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = data.selectedDate.date
                )

            }
        )
        Content(data = data, isValueAvailable = isValueAvailable) { date ->
            data = data.copy(
                selectedDate = date,
                visibleDates = data.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                }
            )
            onDateClickListener(date.date)
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    data: CalendarModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
) {
    Row {
        Text(
            text = if (data.selectedDate.isToday) {
                stringResource(id = R.string.calendar_today)
            } else {
                data.selectedDate.date.toLocaleFormat()
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    data: CalendarModel,
    isValueAvailable: List<LocalDate>,
    onDateClickListener: (CalendarModel.Date) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 48.dp)) {
        items(data.visibleDates.size) { index ->
            ContentItem(
                date = data.visibleDates[index],
                isValueAvailable = isValueAvailable.any {
                    it.isEqual(data.visibleDates[index].date)
                },
                onDateClickListener
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentItem(
    date: CalendarModel.Date,
    isValueAvailable: Boolean,
    onClickListener: (CalendarModel.Date) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                onClickListener(date)
            }
            .border(
                border = BorderStroke(
                    width = if (date.isSelected) 1.5.dp else 0.dp,
                    color = if (date.isSelected) grey50 else Color.White
                ), shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                grey50
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.day,
                style = MaterialTheme.typography.bodySmall,
                color = if (date.isSelected) Black40 else Color.White
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (date.isSelected) Black40 else Color.White
            )
            if (isValueAvailable) {
                Spacer(modifier = Modifier.height(4.dp))
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    drawCircle(
                        color = if (date.isSelected) Black40 else primaryColor,
                        radius = 3.dp.toPx()
                    )
                }
            }
        }
    }
}