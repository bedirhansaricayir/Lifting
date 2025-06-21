package com.lifting.app.core.ui.components.chart_redesig

/**
 * Created by bedirhansaricayir on 03.06.2025
 */

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.pow

fun Path.addRoundRect(
    rect: Rect,
    radius: Bars.Data.RadiusPx
){
    when (radius) {
        is Bars.Data.RadiusPx.None -> {
            addRect(rect)
        }

        is Bars.Data.RadiusPx.Circular -> {
            addRoundRect(
                roundRect = RoundRect(
                    rect = rect,
                    cornerRadius = CornerRadius(
                        x = radius.radius,
                        y = radius.radius
                    )
                )
            )
        }

        is Bars.Data.RadiusPx.Rectangle -> {
            addRoundRect(
                roundRect = RoundRect(
                    rect = rect,
                    topLeft = CornerRadius(
                        x = radius.topLeft,
                        y = radius.topLeft
                    ),
                    topRight = CornerRadius(
                        x = radius.topRight,
                        y = radius.topRight
                    ),
                    bottomLeft = CornerRadius(
                        x = radius.bottomLeft,
                        y = radius.bottomLeft
                    ),
                    bottomRight = CornerRadius(
                        x = radius.bottomRight,
                        y = radius.bottomRight
                    ),
                )
            )
        }
    }
}

fun DrawScope.drawGridLines(
    dividersProperties: DividerProperties,
    indicatorPosition: IndicatorPosition,
    gridEnabled: Boolean,
    xAxisProperties: GridProperties.AxisProperties,
    yAxisProperties: GridProperties.AxisProperties,
    size: Size? = null,
    xPadding: Float = 0f,
    yPadding: Float = 0f
) {

    val _size = size ?: this.size

    val xAxisPathEffect = xAxisProperties.style.pathEffect
    val yAxisPathEffect = yAxisProperties.style.pathEffect


    if (xAxisProperties.enabled && gridEnabled) {
        for (i in 0 until xAxisProperties.lineCount) {
            val y = _size.height.spaceBetween(itemCount = xAxisProperties.lineCount, index = i)
            drawLine(
                brush = xAxisProperties.color,
                start = Offset(0f + xPadding, y + yPadding),
                end = Offset(_size.width + xPadding, y + yPadding),
                strokeWidth = xAxisProperties.thickness.toPx(),
                pathEffect = xAxisPathEffect
            )
        }
    }
    if (yAxisProperties.enabled && gridEnabled) {
        for (i in 0 until yAxisProperties.lineCount) {
            val x = _size.width.spaceBetween(itemCount = yAxisProperties.lineCount, index = i)
            drawLine(
                brush = yAxisProperties.color,
                start = Offset(x + xPadding, 0f + yPadding),
                end = Offset(x + xPadding, _size.height + yPadding),
                strokeWidth = yAxisProperties.thickness.toPx(),
                pathEffect = yAxisPathEffect
            )
        }
    }
    if (dividersProperties.xAxisProperties.enabled && dividersProperties.enabled) {
        val y = if (indicatorPosition == IndicatorPosition.Vertical.Top) 0f else _size.height
        drawLine(
            brush = dividersProperties.xAxisProperties.color,
            start = Offset(0f + xPadding, y + yPadding),
            end = Offset(_size.width + xPadding, y + yPadding),
            strokeWidth = dividersProperties.xAxisProperties.thickness.toPx(),
            pathEffect = dividersProperties.xAxisProperties.style.pathEffect
        )
    }
    if (dividersProperties.yAxisProperties.enabled && dividersProperties.enabled) {
        val x = if (indicatorPosition == IndicatorPosition.Horizontal.End)  _size.width else  0f
        drawLine(
            brush = dividersProperties.yAxisProperties.color,
            start = Offset(x + xPadding, 0f + yPadding),
            end = Offset(x + xPadding, _size.height + yPadding),
            strokeWidth = dividersProperties.yAxisProperties.thickness.toPx(),
            pathEffect = dividersProperties.yAxisProperties.style.pathEffect
        )
    }
}


fun Double.format(decimalPlaces: Int): String {
    require(decimalPlaces >= 0) { "Decimal places must be non-negative." }

    if (decimalPlaces == 0) {
        return this.toInt().toString() // Handle whole numbers efficiently
    }

    val factor = 10.0.pow(decimalPlaces.toDouble())
    val roundedValue = kotlin.math.round(this * factor) / factor
    return roundedValue.toString()
}

fun Float.spaceBetween(itemCount: Int, index: Int): Float {
    if (itemCount == 1) return 0f
    val itemSize = this / (itemCount - 1)
    val positions = (0 until itemCount).map { it * itemSize }
    val result = positions[index]
    return result
}

fun split(
    count: IndicatorCount,
    minValue: Double,
    maxValue: Double,
):List<Double>{
    return when (count) {
        is IndicatorCount.CountBased -> {
            val step = (maxValue - minValue) / (count.count - 1)
            val result = (0 until count.count).map { (maxValue - it * step) }
            result
        }

        is IndicatorCount.StepBased -> {
            val result = mutableListOf<Double>()
            var cache = maxValue
            while (cache > minValue) {
                result.add(cache.coerceAtLeast(minValue))
                cache -= count.stepBy
            }
            result
        }
    }
}