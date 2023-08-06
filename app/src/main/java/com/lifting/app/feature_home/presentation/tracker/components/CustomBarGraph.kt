package com.lifting.app.feature_home.presentation.tracker.components

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.round

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BarGraph(
    graphBarData: List<Float>,
    xAxisScaleData: MutableList<String>,
    barData_: MutableList<Int>,
    height: Dp,
    roundType: BarType,
    barWidth: Dp,
    barColor: Color,
    barArrangement: Arrangement.Horizontal,
    onColumnClick: (String, Int) -> Unit,
    onColumnLongClicked: (String, Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
        Paint().apply {
            color = Color.White.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val yCoordinates = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val lineHeightXAxis = 10.dp
    val horizontalLineHeight = 5.dp
    val maxValue = barData.maxOrNull()?.toFloat() ?: 0f

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .padding(top = xAxisScaleHeight, end = 3.dp)
                .height(height)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxSize()
            ) {
                val yAxisScaleText = maxValue / 3f
                (0..3).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            round(yAxisScaleText * i).toString(),
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
                                    .combinedClickable(
                                        onClick = {
                                            onColumnClick(xAxisScaleData[index],barData[index])
                                        },
                                        onLongClick = {
                                            onColumnLongClicked(xAxisScaleData[index],barData[index])
                                        })
                            )
                        }
                        Column(
                            modifier = Modifier.height(xAxisScaleHeight),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
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