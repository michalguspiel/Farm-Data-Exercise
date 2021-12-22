package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot
import java.text.DecimalFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun CustomLineGraph(lines: List<DataPoint>, dates: List<String>, sensorType: String) {
    com.madrapps.plot.line.LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    transformList(lines),
                    LinePlot.Connection(color = Color.Blue),
                    LinePlot.Intersection(color = Color.Blue, radius = 1.dp, alpha = 0.8f),
                    LinePlot.Highlight(color = Color.Blue),
                    LinePlot.AreaUnderLine(color = Color(0xFF4747FF))
                )
            ), isZoomAllowed = false,
            selection = LinePlot.Selection(enabled = false) ,
            grid = LinePlot.Grid(Color.LightGray, steps = countYSteps(lines)),
            xAxis = LinePlot.XAxis(roundToInt = false,
                content = { min, offset, max ->
                    for (it in dates.indices) {
                        val value = it * offset + min
                        Text(
                            text = "",
                            maxLines = 1,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface
                        )
                        if (value > max) {
                            break
                        }

                    }
                }),
            yAxis = LinePlot.YAxis(
                roundToInt = sensorType != "pH", steps = 5,paddingStart = 6.dp,paddingEnd = 0.dp,
                content = { min, offset, _ ->
                    for (it in 0 until 5) {
                        val value = it * offset + min
                        Text(
                            text = value.string(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                })
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),

    )


}

private fun transformList(list: List<DataPoint>): List<DataPoint> {
    val listOfRatios = computeListOfXValuesAsRatios(list)
    val newList: MutableList<DataPoint> = mutableListOf()
    for (dataPoint in list) {
        val newDataPoint =
            DataPoint(listOfRatios[list.indexOf(dataPoint)], list[list.indexOf(dataPoint)].y)
        newList.add(newDataPoint)
    }
    return newList
}

private fun computeListOfXValuesAsRatios(list: List<DataPoint>): List<Float> {
    val zeroValue = list.first().x
    val listOfValues = list.map { it.x - zeroValue }
    val lastValue = listOfValues.last()
    return listOfValues.map { (it * 100 / lastValue) / 5.75f }
}


private fun Float.string() = DecimalFormat("#.#").format(this)


private fun countYSteps(list: List<DataPoint>): Int {
    val values = list.map { it.y.toInt() }
    val min = values.minOrNull()
    val max = values.maxOrNull()
    return if (min != null && max != null) {
        val difference = max - min
        if (difference <= 2) {
            val floatValues = list.map { it.y }
            val floatMin = floatValues.minOrNull()
            val floatMax = floatValues.maxOrNull()
            val floatDifference = floatMax!! - floatMin!!
            return (floatDifference / 0.2).toInt()
        }
        when {
            difference <= 5 -> difference
            difference <= 10 -> difference / 2
            difference <= 30 -> difference / 3
            difference <= 50 -> difference / 4
            else -> 12
        }
    } else 0


}