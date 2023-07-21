package com.lifting.app.feature_home.presentation.tracker


import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.tracker.chart.BarGraph
import com.lifting.app.feature_home.presentation.tracker.chart.BarType
import com.lifting.app.feature_home.presentation.tracker.dialog.CustomTrackingDialog
import com.lifting.app.theme.black20
import com.lifting.app.theme.grey50
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val currentDate = getCurrentDateFormatted()
        var dataList by remember { mutableStateOf(mutableListOf(80,82,83)) }
        var datesList by remember { mutableStateOf(mutableListOf("15.06.23", "15.06.23", "17.06.23")) }
        val floatValue = dataList.map { value -> value.toFloat() / dataList.maxOrNull()!! }
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
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.Analiz),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    BarGraph(
                        graphBarData = floatValue,
                        xAxisScaleData = datesList,
                        barData_ = dataList,
                        height = 300.dp,
                        roundType = BarType.TOP_CURVED,
                        barWidth = 20.dp,
                        barColor = MaterialTheme.colorScheme.primary,
                        barArrangement = Arrangement.SpaceEvenly,
                        onColumnClick = { tarih, kilo ->
                            Toast
                                .makeText(
                                    context,
                                    "$tarih Tarihinde $kilo Kilogramdınız.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                        onColumnLongClicked = { tarih, kilo ->
                            Toast
                                .makeText(
                                    context,
                                    "LongClick",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    )
                }
            }
        }

        if (onFabClick) {
            CustomTrackingDialog(
                dialogState = onFabClick,
                onDissmiss = { onFabClick = !onFabClick },
                onSaveButtonClicked = {
                    dataList.add(it)
                    datesList.add(currentDate)
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

@Composable
fun getCurrentDateFormatted(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return dateFormat.format(currentDate)
}