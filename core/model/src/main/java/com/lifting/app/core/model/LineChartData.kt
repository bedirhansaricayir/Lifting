package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 13.05.2025
 */

data class LineChartData(
    val points: List<Point>,
    /** This is percentage we pad yValue by.**/
    val padBy: Float = 20f,
    val startAtZero: Boolean = false
) {
    init {
        require(padBy in 0f..100f)
    }

    val yMinMax: Pair<Float, Float>
        get() {
            val min = points.minByOrNull { it.value }?.value ?: 0f
            val max = points.maxByOrNull { it.value }?.value ?: 0f

            return min to max
        }

    val maxYValue: Float =
        yMinMax.second + ((yMinMax.second - yMinMax.first) * padBy / 100f)

    val minYValue: Float
        get() {
            return if (startAtZero) {
                0f
            } else {
                yMinMax.first - ((yMinMax.second - yMinMax.first) * padBy / 100f)
            }
        }

    val yRange = maxYValue - minYValue

    data class Point(val value: Float, val label: String)
}