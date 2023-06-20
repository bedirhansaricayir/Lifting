package com.fitness.app.presentation.tracker


import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.LineDrawer
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.line.SolidLineShader
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.round

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val currentDate = getCurrentDateFormatted()
        var dataList by remember { mutableStateOf(mutableListOf(1)) }
        var datesList by remember { mutableStateOf(mutableListOf(currentDate)) }
        var floatValue = mutableListOf<Float>()
        var onFabClick by remember { mutableStateOf(false) }
        var bodyWeightList2 by remember { mutableStateOf(listOf(80,82,83)) }
        var dateList2 by remember { mutableStateOf(listOf("15.06.23", "15.06.23", "17.06.23")) }

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
                     fun randomYValue(): Float = (100f * Math.random()).toFloat() + 45f

                    var lineChartData by mutableStateOf(
                        LineChartData(points = listOf(
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate),
                                LineChartData.Point(randomYValue(), currentDate)
                        ), lineDrawer = SolidLineDrawer(color = MaterialTheme.colorScheme.primary),
                        )
                    )

                    var lineChartData2 by mutableStateOf(
                        LineChartData(
                            points = listOf(
                                LineChartData.Point(randomYValue(), "Label1"),
                                LineChartData.Point(randomYValue(), "Label2"),
                                LineChartData.Point(randomYValue(), "Label3"),

                                ),
                            lineDrawer = SolidLineDrawer(
                                color = Color(0xFF00FF00)
                            )
                        )
                    )
                    var horizontalOffset by  remember {
                        mutableStateOf(5f)
                    }

                    /*LineChart(linesChartData = listOf(lineChartData),
                        horizontalOffset = horizontalOffset,
                        pointDrawer = FilledCircularPointDrawer(),)*/
                     LineChartView(bodyWeightList = bodyWeightList2, dateList = dateList2)
                }
            }
        }

        if (onFabClick) {
            CustomTrackingDialog(
                dialogState = onFabClick,
                onDissmiss = { onFabClick = !onFabClick },
                onSaveButtonClicked = {
                    bodyWeightList2 = bodyWeightList2 + it
                    dateList2 = dateList2 + currentDate

                    LineChartData.Point(it.toFloat(),currentDate)
                    dataList.add(it)
                    datesList.add(currentDate)
                    onFabClick = !onFabClick
                }
            )
        }
    }

}

@Composable
fun LineChartView(
    bodyWeightList: List<Int>,
    dateList: List<String>,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    val maxValue = 200
    val minValue = 0
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val startX = 80f // X ekseni başlangıç noktasının x koordinatı
        val endX = size.width - 50f
        val startY = size.height - 80f // Y ekseni başlangıç noktasının y koordinatı
        val endY = 50f

        // Y ekseni için ek düzenlemeler
        val yRange = maxValue - minValue
        val yInterval = yRange / 10

        // X ekseni için ek düzenlemeler
        val xRange = dateList.size - 1
        val xInterval = (endX - startX - 40f) / xRange

        // Veri noktalarını ve dalgalı çizgiyi çizme
        val path = Path()
        for (i in bodyWeightList.indices) {
            val x = startX + (i * xInterval) + 20f
            val y = startY - ((bodyWeightList[i] - minValue) * (startY - endY) / yRange)

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                val previousX = startX + ((i - 1) * xInterval) + 20f
                val previousY = startY - ((bodyWeightList[i - 1] - minValue) * (startY - endY) / yRange)

                val controlPointX1 = previousX + (x - previousX) / 2
                val controlPointY1 = previousY
                val controlPointX2 = previousX + (x - previousX) / 2
                val controlPointY2 = y

                path.cubicTo(
                    controlPointX1, controlPointY1,
                    controlPointX2, controlPointY2,
                    x, y
                )
            }


            drawCircle(
                color = circleColor,
                radius = 12f,
                center = Offset(x, y)
            )
        }
        // Dalgalı çizgiyi çizme
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 12f, cap = StrokeCap.Round)
        )

        // X ekseni çizgisi
        drawLine(
            start = Offset(startX, startY),
            end = Offset(endX, startY),
            color = Color.Gray,
            strokeWidth = 6f
        )

        // Y ekseni çizgisi
        drawLine(
            start = Offset(startX, startY),
            end = Offset(startX, endY),
            color = Color.Gray,
            strokeWidth = 6f
        )

        // Y ekseni etiketleri
        for (i in 0..10) {
            val yValue = minValue + i * yInterval
            val y = startY - (yValue * (startY - endY) / yRange)
            val yText = yValue.toString()

            /*
            Y eksenindeki sayılara denk gelen çentik çizimi
            drawLine(
                start = Offset(startX - 10f, y),
                end = Offset(startX, y),
                color = Color.Gray,
                strokeWidth = 2f
            )*/

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    yText,
                    startX - 30f,
                    y + 10f,
                    Paint().apply {
                        color = Color.Black.hashCode()
                        textSize = 20f
                        textAlign = Paint.Align.RIGHT
                    }
                )
            }
        }

        // X ekseni etiketleri
        for (i in dateList.indices) {
            val x = startX  + (i * xInterval) + 20f
            val y = startY + 30f

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    dateList[i],
                    x,
                    y,
                    Paint().apply {
                        color = Color.Black.hashCode()
                        textSize = 30f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
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
    val context = LocalContext.current

    val barData by remember {
        mutableStateOf(barData_)
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
                                        Toast
                                            .makeText(
                                                context,
                                                "${xAxisScaleData[index]} Tarihinde ${barData[index]} Kilogramdınız.",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
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