package com.fitness.app.presentation.tracker


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.NumberPicker
import com.fitness.app.R
import com.fitness.app.ui.theme.black20
import com.fitness.app.ui.theme.grey30
import com.fitness.app.ui.theme.grey50
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val currentDate = getCurrentDateFormatted()
        var dataList by remember { mutableStateOf(mutableListOf(80, 85, 90, 88)) }
        var datesList by remember { mutableStateOf(mutableListOf("15.06.23", "15.06.23", "17.06.23", currentDate)) }
        var floatValue = mutableListOf<Float>()
        var onFabClick by remember { mutableStateOf(false) }

        dataList.forEachIndexed { index, value ->
            floatValue.add(index = index, element = value.toFloat() / dataList.max().toFloat())

        }
        Scaffold(
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
fun BarGraph(
    graphBarData: List<Float>,
    xAxisScaleData: MutableList<String>,
    barData_: MutableList<Int>,
    height: Dp,
    roundType: BarType,
    barWidth: Dp,
    barColor: Color,
    barArrangement: Arrangement.Horizontal
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val barData by remember {
        mutableStateOf(barData_ + 0)
    }

    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp

    val xAxisScaleHeight = 40.dp

    val yAxisScaleSpacing by remember {
        mutableStateOf(100f)
    }
    val yAxisTextWidth by remember {
        mutableStateOf(100.dp)
    }

    val barShap =
        when (roundType) {
            BarType.CIRCULAR_TYPE -> CircleShape
            BarType.TOP_CURVED -> RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
        }

    val density = LocalDensity.current

    val textPaint = remember(density) {
        android.graphics.Paint().apply {
            color = Color.White.hashCode()
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val yCoordinates = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val lineHeightXAxis = 10.dp
    val horizontalLineHeight = 5.dp




    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .padding(top = xAxisScaleHeight, end = 3.dp)
                .height(height)
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxSize()
            ) {
                val yAxisScaleText = (barData.max()) / 3f
                (0..3).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            round(barData.min() + yAxisScaleText * i).toString(),
                            30f,
                            size.height - yAxisScaleSpacing - i * size.height / 3f,
                            textPaint
                        )
                    }
                    yCoordinates.add(size.height - yAxisScaleSpacing - i * size.height / 3f)
                }
                (1..3).forEach {
                    drawLine(
                        start = Offset(x = yAxisScaleSpacing + 30f, y = yCoordinates[it]),
                        end = Offset(x = size.width, y = yCoordinates[it]),
                        color = Color.Gray,
                        strokeWidth = 5f,
                        pathEffect = pathEffect
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 50.dp)
                .width(width - yAxisTextWidth)
                .height(height + xAxisScaleHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState
            ) {
                if (graphBarData.isNotEmpty()) {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(graphBarData.size - 1)
                    }
                }
                items(graphBarData.size) { index ->
                    var animationTriggered by remember { mutableStateOf(false) }
                    val graphBarHeight by animateFloatAsState(
                        targetValue = if (animationTriggered) graphBarData[index] else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 0
                        )
                    )
                    LaunchedEffect(key1 = true) {
                        animationTriggered = true
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .clip(barShap)
                                .width(barWidth)
                                .height(height - 10.dp)
                                .background(Color.Transparent),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(barShap)
                                    .fillMaxWidth()
                                    .fillMaxHeight(graphBarHeight)
                                    .background(barColor)
                                    .clickable {
                                        /*Toast
                                            .makeText(
                                                context,
                                                "${xAxisScaleData[index]} Tarihinde ${barData[index]} Kilogramdınız.",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()*/
                                    }
                            )
                        }
                        Column(
                            modifier = Modifier.height(xAxisScaleHeight),
                            verticalArrangement = Top,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 2.dp,
                                            bottomEnd = 2.dp
                                        )
                                    )
                                    .width(horizontalLineHeight)
                                    .height(lineHeightXAxis)
                                    .background(color = Color.Gray)
                            )
                            Text(
                                modifier = Modifier.padding(bottom = 3.dp, end = 5.dp),
                                text = xAxisScaleData[index],
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = xAxisScaleHeight + 3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .height(horizontalLineHeight)
                        .background(Color.Gray)
                )
            }
        }
    }


}

@Composable
fun CustomTrackingDialog(
    dialogState: Boolean,
    onDissmiss: () -> Unit,
    onSaveButtonClicked: (Int) -> Unit
) {
    var vucutAgirligi by remember { mutableStateOf(75) }
    val showDialog = remember { mutableStateOf(dialogState) }
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = grey30
                )
            ) {
                Text(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(8.dp),
                    text = "Vücut ağırlığını güncelle",
                    style = MaterialTheme.typography.titleSmall
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { vucutAgirligi += 1 },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = black20,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Arttır Butonu"
                        )
                    }
                    NumberPicker(
                        textStyle = TextStyle(color = Color.White),
                        value = vucutAgirligi,
                        onValueChange = {
                            vucutAgirligi = it
                        }, range = 0..200,
                        dividersColor = MaterialTheme.colorScheme.primary
                    )
                    IconButton(
                        onClick = { vucutAgirligi -= 1 },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = black20,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Azalt Butonu"
                        )
                    }
                }


                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(CenterHorizontally)
                        .padding(16.dp),
                    onClick = { onSaveButtonClicked.invoke(vucutAgirligi) },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.Güncelle),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                    )
                }


            }
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