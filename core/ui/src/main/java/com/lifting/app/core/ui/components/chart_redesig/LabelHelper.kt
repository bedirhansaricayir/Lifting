package com.lifting.app.core.ui.components.chart_redesig

/**
 * Created by bedirhansaricayir on 03.06.2025
 */

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun LabelHelper(
    data: List<Pair<String, Brush>>,
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 13.sp),
    labelCountPerLine: Int
) {
    val numberOfGridCells = min(data.size, labelCountPerLine)
    LazyVerticalGrid(columns = GridCells.Fixed(numberOfGridCells), modifier = Modifier) {
        items(data) { (label, color) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                BasicText(
                    text = label,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * RC means Row & Column
 */
@Composable
fun RCChartLabelHelper(
    data: List<Bars>,
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 13.sp),
    labelCountPerLine: Int
) {
    val labels = data.flatMap { it.values.map { it.label } }.distinct()
    val colors = labels.map { label ->
        data.flatMap { bars ->
            bars.values.filter { it.label == label }.map { it.color }
        }.firstOrNull() ?: SolidColor(Color.Unspecified)
    }
    LabelHelper(
        data = labels.mapIndexed { index, label -> label.orEmpty() to colors[index] },
        textStyle = textStyle,
        labelCountPerLine
    )
}

@Composable
fun ImplementRCAnimation(
    data:List<Bars>,
    animationMode: AnimationMode,
    spec: (Bars.Data)->AnimationSpec<Float>,
    delay:Long,
    before:()->Unit
) {
    LaunchedEffect(data) {
        before()
        delay(delay)
        data.map { it.values }.flatten().filter { it.value != 0.0 }.forEachIndexed { index, data ->
            val animate: suspend () -> Unit = {
                data.animator.animateTo(
                    1f,
                    animationSpec = spec(data)
                )
            }
            when (animationMode) {
                is AnimationMode.OneByOne -> {
                    animate()
                }

                is AnimationMode.Together -> {
                    launch {
                        delay(animationMode.delayBuilder(index))
                        animate()
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalLabels(
    labelProperties: LabelProperties,
    labels: List<String>,
    indicatorProperties: HorizontalIndicatorProperties,
    chartWidth: Float,
    density: Density,
    textMeasurer: TextMeasurer,
    xPadding: Float
) {
    if (labelProperties.enabled && labels.isNotEmpty()) {
        Spacer(modifier = Modifier.height(labelProperties.padding))

        val widthModifier =
            if (indicatorProperties.position == IndicatorPosition.Horizontal.End) {
                Modifier.width((chartWidth / density.density).dp)
            } else {
                Modifier.fillMaxWidth()
            }

        val labelMeasures =
            labels.map {
                textMeasurer.measure(
                    it,
                    style = labelProperties.textStyle,
                    maxLines = 1
                )
            }
        val labelWidths = labelMeasures.map { it.size.width }
        val maxLabelWidth = labelWidths.max()
        val minLabelWidth = labelWidths.min()

        var textModifier: Modifier = Modifier
        var shouldRotate = labelProperties.rotation.mode == LabelProperties.Rotation.Mode.Force
        if ((maxLabelWidth / minLabelWidth.toDouble()) >= 1.5 && labelProperties.rotation.degree != 0f) {
            textModifier = textModifier.width((minLabelWidth / density.density).dp)
            shouldRotate = true
        }
        Row(
            modifier = widthModifier
                .padding(
                    start = (xPadding / density.density).dp,
                ), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEachIndexed { index, label ->
                val modifier= if (shouldRotate) textModifier.graphicsLayer {
                    rotationZ = labelProperties.rotation.degree
                    transformOrigin =
                        TransformOrigin(
                            (labelMeasures[index].size.width / minLabelWidth.toFloat()),
                            .5f
                        )
                    translationX =
                        (-(labelMeasures[index].size.width - minLabelWidth.toFloat())) - (labelProperties.rotation.padding?.toPx()
                            ?: (minLabelWidth / 2f))
                } else textModifier
                if (labelProperties.builder != null) {
                    labelProperties.builder.invoke(modifier,label,shouldRotate,index)
                }else{
                    BasicText(
                        modifier = modifier,
                        text = label,
                        style = labelProperties.textStyle.copy(color = labelProperties.textColor),
                        overflow = if (shouldRotate) TextOverflow.Visible else TextOverflow.Clip,
                        softWrap = !shouldRotate,
                    )
                }

            }
        }
    }
}

fun checkRCMaxValue(maxValue: Double, data: List<Bars>) {
    require(maxValue >= (data.maxOfOrNull { it.values.maxOfOrNull { it.value } ?: 0.0 } ?: 0.0)) {
        "Chart data must be at most $maxValue (Specified Max Value)"
    }
}

fun checkRCMinValue(minValue: Double, data: List<Bars>) {
    require(minValue <= 0) {
        "Min value in column chart must be 0 or lower."
    }
    require(minValue <= (data.minOfOrNull { it.values.minOfOrNull { it.value } ?: 0.0 } ?: 0.0)) {
        "Chart data must be at least $minValue (Specified Min Value)"
    }
}

fun calculateOffset(
    maxValue: Double,
    minValue: Double,
    total: Float,
    value:Float
): Double {
    val range = maxValue - minValue
    val percentage = (value - minValue) / range
    val offset = total * percentage
    return offset
}

fun calculateValue(minValue: Double, maxValue: Double, total: Float, offset:Float): Double {
    val percentage = offset / total
    val range = maxValue - minValue
    val value = minValue + percentage * range
    return value
}