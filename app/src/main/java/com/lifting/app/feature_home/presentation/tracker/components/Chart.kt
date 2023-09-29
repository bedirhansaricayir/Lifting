package com.lifting.app.feature_home.presentation.tracker.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.theme.Black40
import com.lifting.app.theme.White40
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartState: List<ChartState>,
    isCircleVisible: Boolean,
    isValuesVisible: Boolean,
    isMoveViewToAnimated: Boolean,
    setDrawFilled: Boolean,
    onValueSelected: (chartState: ChartState) -> Unit,
) {

    val label = "Your Bodyweight Datas"
    val primaryColor = MaterialTheme.colorScheme.primary

    if (chartState.isNotEmpty()) {
        val entries = chartState.mapIndexed { index, state ->
            Entry(index.toFloat(), state.data)
        }

        val xAxisformatter = IndexAxisValueFormatter(
            chartState.map { state ->
                val today: LocalDate = state.date
                val dateformatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")
                today.format(dateformatter)
            }
        )

        val leftAxisFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                val yValue = value.toInt()
                return if (yValue == 0) {
                    ""
                } else {
                    "$yValue"
                }
            }
        }
        val lineDataSet = LineDataSet(entries, label).apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = primaryColor.toArgb()
            highLightColor = primaryColor.toArgb()
            lineWidth = 1.5f
            setDrawFilled(setDrawFilled)
            fillColor = primaryColor.copy(alpha = 0.5f).toArgb()
            fillAlpha = 70
            setDrawCircles(isCircleVisible)
            setCircleColor(primaryColor.toArgb())
            circleHoleColor = Black40.toArgb()
            setDrawValues(isValuesVisible)
            valueTextSize = 5f
            valueTextColor = primaryColor.toArgb()
        }


        AndroidView(
            factory = { context ->
                Utils.init(context)
                LineChart(context).apply {
                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            h?.let { onValueSelected(chartState[h.x.toInt()]) }
                        }

                        override fun onNothingSelected() {
                            Log.d("seçilmedi", "Seçilmedi")
                        }

                    })
                    //animateX(500, Easing.EaseInBounce)
                    animateXY(500, 500)
                    description.isEnabled = false
                    isDragEnabled = true
                    setTouchEnabled(true)
                    setScaleEnabled(true)
                    setPinchZoom(true)
                    legend.isEnabled = false
                    legend.form = Legend.LegendForm.CIRCLE
                    isDoubleTapToZoomEnabled = false
                    isHighlightPerDragEnabled = true
                    isHorizontalScrollBarEnabled = true
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        setDrawLabels(true)
                        isEnabled = false
                        textSize = 5f
                        textColor = White40.toArgb()
                        valueFormatter = xAxisformatter

                    }
                    axisLeft.apply {
                        //axisMinimum = 0f  Y eksenini 0'dan başlatır.
                        textSize = 8f
                        textColor = White40.toArgb()
                        setDrawAxisLine(false)
                        valueFormatter = leftAxisFormatter

                    }
                    axisRight.apply {
                        isEnabled = false
                    }
                }
            },
            update = {
                it.apply {
                    data = LineData(lineDataSet)
                    //invalidate()
                    setVisibleXRangeMaximum(30f)
                    if (!isMoveViewToAnimated) moveViewToX(entries.size.toFloat())
                    else moveViewToAnimated(
                        entries.size.toFloat(),
                        10f,
                        YAxis.AxisDependency.RIGHT,
                        1000
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
        )
    }
}
