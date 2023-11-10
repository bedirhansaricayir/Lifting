package com.lifting.app.feature_tracker.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_tracker.domain.model.ChartState
import java.time.LocalDate

@Composable
fun ExpandableList(
    list: List<ChartState>,
    currentDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        var currentDateMatched = false
        list.forEach { data ->
            if (data.date == currentDate) {
                ExpandableTableCard(chartState = data,modifier = Modifier.padding(8.dp))
                currentDateMatched = true
            }
        }
        if (!currentDateMatched) {
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.nothing_found_label), style = MaterialTheme.typography.labelMedium,color = Color.LightGray)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableTableCard(chartState: ChartState, modifier: Modifier = Modifier) {

    val textScrollState = rememberScrollState()
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Unspecified),
        border = BorderStroke(1.dp, color = Color.White),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = chartState.data.toString(),
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = chartState.date.toLocaleFormat(),
                modifier = Modifier,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium
            )
            IconButton(modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(rotationState), onClick = { expandedState = !expandedState }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Description",
                    tint = Color.White
                )
            }

        }
        if (expandedState) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Text(
                modifier = Modifier
                    .verticalScroll(textScrollState)
                    .padding(8.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                text = chartState.description
            )
        }


    }

}
