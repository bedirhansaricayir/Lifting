package com.fitness.app.presentation.tracker


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
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val dataList = mutableListOf(
            30,
            60,
            90,
            50,
            70,
            130,
            128,
            123,
            122,
            120,
            60,
            90,
            50,
            70,
            130,
            128,
            123,
            122,
            120,
            60,
            90,
            133,
            70,
            90,
            95,
            100,
            105,
            110
        )
        val floatValue = mutableListOf<Float>()
        val currentDate = getCurrentDateFormatted()

        val datesList = mutableListOf(
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate,
            currentDate
        )
        dataList.forEachIndexed { index, value ->

            floatValue.add(index = index, element = value.toFloat() / dataList.max().toFloat())

        }
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

@Composable
fun BarGraph(
    graphBarData: List<Float>,
    xAxisScaleData: MutableList<String>,
    barData_: List<Int>,
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
        mutableStateOf(barData_ + 0)
    }

    // for getting screen width and height you can use LocalConfiguration
    val configuration = LocalConfiguration.current
    // getting screen width
    val width = configuration.screenWidthDp.dp

    // bottom height of the X-Axis Scale
    val xAxisScaleHeight = 40.dp

    val yAxisScaleSpacing by remember {
        mutableStateOf(100f)
    }
    val yAxisTextWidth by remember {
        mutableStateOf(100.dp)
    }

    // bar shape
    val barShap =
        when (roundType) {
            BarType.CIRCULAR_TYPE -> CircleShape
            BarType.TOP_CURVED -> RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
        }

    val density = LocalDensity.current
    // y-axis scale text paint
    val textPaint = remember(density) {
        android.graphics.Paint().apply {
            color = Color.White.hashCode()
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    // for y coordinates of y-axis scale to create horizontal dotted line indicating y-axis scale
    val yCoordinates = mutableListOf<Float>()
    // for dotted line effect
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    // height of vertical line over x-axis scale connecting x-axis horizontal line
    val lineHeightXAxis = 10.dp
    // height of horizontal line over x-axis scale
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
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(graphBarData.size - 1)
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
fun getCurrentDateFormatted(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    return dateFormat.format(currentDate)
}