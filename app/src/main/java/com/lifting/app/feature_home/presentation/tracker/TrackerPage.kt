package com.lifting.app.feature_home.presentation.tracker


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.tracker.components.Chart
import com.lifting.app.feature_home.presentation.tracker.components.TimeRangePicker
import com.lifting.app.feature_home.presentation.tracker.components.CustomTrackingDialog
import com.lifting.app.theme.black20
import com.lifting.app.theme.grey50
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen(
    state: TrackerPageUiState,
    onEvent: (TrackerPageEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var onFabClick by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            floatingActionButton = {
                CustomFloatingActionButton {
                    onFabClick = !onFabClick
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = grey50)
                    .padding(it.calculateBottomPadding())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()
                ) {
                    Text(
                        text = stringResource(id = R.string.Analiz),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    TimeRangePicker(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp), selectedTimeRange = state.getTimeRange()){ timeRange ->
                        onEvent(TrackerPageEvent.OnTimeRangeClicked(timeRange))
                    }
                    Chart(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),chartState = state.chartState,isCircleVisible = true, isValuesVisible = true) { val1, val2 ->
                        Log.d("OnChartValueSelected","$val1 Tarihinde $val2 Kilogram")
                    }
                }
            }
        }

        if (onFabClick) {
            CustomTrackingDialog(
                dialogState = onFabClick,
                onDissmiss = { onFabClick = !onFabClick },
                onSaveButtonClicked = {
                    onEvent(TrackerPageEvent.OnDialogButtonClicked(localDate = LocalDate.now(), bw = it.toFloat()))
                    onFabClick = !onFabClick
                }
            )
        }
    }

}

@Composable
fun CustomFloatingActionButton(
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = black20,
        shape = RoundedCornerShape(40.dp),
        onClick = { onFabClicked.invoke() }) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }
}