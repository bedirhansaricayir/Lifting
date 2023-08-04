package com.lifting.app.feature_home.presentation.tracker.components

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.theme.Black40
import com.lifting.app.theme.White40
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartState: List<ChartState>,
    isCircleVisible: Boolean,
    isValuesVisible: Boolean
) {

    val label = "Your Bodyweight Datas"

    if (chartState.isNotEmpty()) {
        val entries = chartState.mapIndexed { index, state ->
            Entry(index.toFloat(), state.bodyweight)
        }

        val xAxisformatter = IndexAxisValueFormatter(
            chartState.map { state ->
                val today: LocalDate = state.dateWithoutTime
                val dateformatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
                today.format(dateformatter)

               /* DateTimeFormatter.ofPattern("dd.MM.yy").format(state.dateWithoutTime)
                DateFormat.getDateInstance(DateFormat.SHORT)
                    .format(state.dateWithoutTime)*/
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
            color = MaterialTheme.colorScheme.primary.toArgb()
            highLightColor = MaterialTheme.colorScheme.primary.toArgb()
            //lineWidth = 2f
            setDrawFilled(true)
            fillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f).toArgb()
            setDrawCircles(isCircleVisible)
            setCircleColor(MaterialTheme.colorScheme.primary.toArgb())
            circleHoleColor = Black40.toArgb()
            setDrawValues(isValuesVisible)
            //valueTextSize = 8f
            valueTextColor = MaterialTheme.colorScheme.primary.toArgb()
        }


        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            Log.d("secildi5",e.toString())
                            Toast.makeText(context,"${e?.x} Tarihinde ${e?.y} Kilogramdınız.",Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected() {
                            Log.d("seçilmedi","Seçilmedi")
                        }

                    })
                    description.isEnabled = false
                    isDragEnabled = false
                    setTouchEnabled(true)
                    setScaleEnabled(false)
                    setPinchZoom(false)
                    legend.isEnabled = false
                    legend.form = Legend.LegendForm.CIRCLE
                    isDoubleTapToZoomEnabled = false
                    isHighlightPerDragEnabled = false
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        setDrawLabels(true)
                        isEnabled = true
                        textSize = 5f
                        textColor = White40.toArgb()
                        valueFormatter = xAxisformatter

                    }
                    axisLeft.apply {
                        axisMinimum = 0f
                        textSize = 8f
                        textColor = White40.toArgb()
                        setDrawAxisLine(false)
                        valueFormatter = leftAxisFormatter

                    }
                    axisRight.apply {
                        isEnabled = false
                    }
                    data = LineData(lineDataSet)
                    invalidate()


                }
            },
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
        )
    }
}
