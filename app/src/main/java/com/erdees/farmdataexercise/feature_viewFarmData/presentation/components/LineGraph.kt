package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.erdees.farmdataexercise.coreUtils.utils.Util.toDp
import com.erdees.farmdataexercise.coreUtils.utils.Util.toPx
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatISO8601String
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.model.CornerStatus
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.model.HorizontalCorner
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.model.VerticalCorner
import com.erdees.farmdataexercise.ui.theme.*
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import java.text.DecimalFormat


@Composable
fun CustomDetailedLineGraph(
    lines: List<DataPoint>,
    dates: List<String>,
    sensorType: String,
    modifier: Modifier = Modifier
) {

    val density = LocalDensity.current

    val yAxisWidth = 36.dp
    val yAxisPadding = 6.dp
    val yAxisTotalWidth = yAxisPadding.value * 2 + yAxisWidth.value

    val totalWidthOfChart = remember { mutableStateOf(0) }
    val totalScreenWidth = remember { mutableStateOf(0) }

    val padding = LocalSpacing.current.medium

    Column(Modifier.onGloballyPositioned {
        totalWidthOfChart.value =
            it.size.width.toFloat().toDp(density).toInt() - yAxisTotalWidth.toInt()
        totalScreenWidth.value = it.size.width
    }) {

        val visibility = remember { mutableStateOf(false) }
        val cardSize = remember { mutableStateOf(IntSize(0, 0)) }
        val offset = remember { mutableStateOf(Offset(0f, 0f)) }
        val adjustedOffset = remember { mutableStateOf(Offset(0f, 0f)) }
        val cornerStatus = remember {
            mutableStateOf(
                CornerStatus(
                    HorizontalCorner.StartCorner,
                    VerticalCorner.TopCorner
                )
            )
        }

        val points = remember { mutableStateOf(listOf<DataPoint>()) }

        val listTransformedIntoRatios = transformList(
            lines,
            lastVisiblePointForScreenWidth(totalWidthOfChart.value)
        )

        Box(Modifier.fillMaxSize()) {
            LineGraph(
                plot = LinePlot(
                    listOf(
                        LinePlot.Line(
                            listTransformedIntoRatios,
                            LinePlot.Connection(color = Green500),
                            LinePlot.Intersection(
                                color = Green400,
                                radius = 2.dp,
                                alpha = 0.8f
                            ),
                            LinePlot.Highlight { center ->
                                offset.value = center
                                val color = Color.Gray
                                drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                                drawCircle(color, 6.dp.toPx(), center)
                                drawCircle(Green500, 3.dp.toPx(), center)
                            },
                            LinePlot.AreaUnderLine(color = Yellow300)
                        )
                    ),
                    horizontalExtraSpace = 0.dp,
                    isZoomAllowed = false,
                    selection = LinePlot.Selection(
                        highlight = LinePlot.Connection(
                            Color.LightGray,
                            strokeWidth = 2.dp,
                        )
                    ),
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
                        roundToInt = sensorType != "pH",
                        steps = 5,
                        paddingStart = yAxisPadding,
                        paddingEnd = yAxisPadding,
                        content = { min, offset, _ ->
                            for (it in 0 until 5) {
                                val value = it * offset + min
                                Text(
                                    text = value.string(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.width(yAxisWidth),
                                    textAlign = TextAlign.Center
                                )
                            }
                        })
                ),
                modifier,
                onSelectionStart = { visibility.value = true },
                onSelectionEnd = { visibility.value = false })
            { _, pts ->
                points.value = pts
                adjustedOffset.value = adjustOffsetOfDataCard(
                    offset.value,
                    cardSize.value,
                    totalScreenWidth.value,
                    padding.toPx(density).toInt(),
                    cornerStatus
                )
            }
            if (visibility.value) {

                Surface(
                    modifier = Modifier
                        .width(200.dp)
                        .onGloballyPositioned {
                            cardSize.value = it.size
                        }
                        .absoluteOffset(
                            x = adjustedOffset.value.x.toDp(density).dp,
                            y = adjustedOffset.value.y.toDp(density).dp
                        )
                        .padding(4.dp)
                        .alpha(0.9f),
                    shape = cornerStatus.value.provideShape(rounding = LocalCorner.current.large),
                    color = Color.Gray
                ) {
                    Column(
                        Modifier
                            .padding(horizontal = LocalSpacing.current.default)
                    ) {
                        val value = points.value
                        if (value.isNotEmpty()) {
                            val date = dates[listTransformedIntoRatios.indexOf(value[0])]
                            val dateFormatted = formatISO8601String(date)
                            Text(
                                modifier = Modifier.padding(vertical = LocalSpacing.current.default),
                                text = dateFormatted,
                                style = MaterialTheme.typography.h6,
                                color = Color.Black
                            )
                            DataRow(value[0].y)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DataRow(value: Float) {
    val formatted = DecimalFormat("#.###").format(value)
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = LocalSpacing.current.default)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Image(
                painter = ColorPainter(Green200),
                contentDescription = "Data decoration circle",
                modifier = Modifier
                    .align(CenterVertically)
                    .size(8.dp)
                    .clip(CircleShape)
                    .shadow(2.dp, CircleShape)
            )
            Spacer(Modifier.padding(LocalSpacing.current.xxSmall))
            Text(
                text = "Value:",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black
            )
        }
        Text(
            modifier = Modifier
                .padding(end = LocalSpacing.current.default)
                .align(Alignment.CenterEnd),
            text = formatted,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W500,
            color = Color.Black
        )
    }
}


/** ADJUST OFFSET OF DATA CARD TO KEEP IT TOTALLY VISIBLE ON THE SCREEN AT ALL TIMES.
 *  */
private fun adjustOffsetOfDataCard(
    offset: Offset,
    cardSize: IntSize,
    totalWidth: Int,
    padding: Int,
    cornerStatus: MutableState<CornerStatus>
): Offset {
    val x = when {
        offset.x + cardSize.width + padding > totalWidth -> {
            cornerStatus.value =
                CornerStatus(HorizontalCorner.EndCorner, cornerStatus.value.verticalCorner)
            offset.x - cardSize.width - padding
        }
        else -> {
            cornerStatus.value =
                CornerStatus(HorizontalCorner.StartCorner, cornerStatus.value.verticalCorner)
            offset.x + padding
        }
    }
    val y = when {
        offset.y - cardSize.height - padding < 0 -> {
            cornerStatus.value =
                CornerStatus(cornerStatus.value.horizontalCorner, VerticalCorner.TopCorner)
            offset.y + padding
        }
        else -> {
            cornerStatus.value =
                CornerStatus(cornerStatus.value.horizontalCorner, VerticalCorner.BottomCorner)
            offset.y - cardSize.height - padding
        }
    }
    return Offset(x, y)
}


@Composable
fun CustomPreviewLineGraph(
    lines: List<DataPoint>,
    dates: List<String>,
    sensorType: String,
    modifier: Modifier = Modifier
) {

    val yAxisWidth = 36.dp
    val yAxisPadding = 6.dp
    val yAxisTotalWidth = yAxisPadding.value * 2 + yAxisWidth.value

    val density = LocalDensity.current

    val totalWidthOfChart = remember { mutableStateOf(0) }
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    transformList(
                        lines,
                        lastVisiblePointForScreenWidth(totalWidthOfChart.value)
                    ),
                    LinePlot.Connection(color = Green500),
                    LinePlot.Intersection(color = Green500, radius = 1.dp, alpha = 0.8f),
                    LinePlot.Highlight(color = Green500),
                    LinePlot.AreaUnderLine(color = Yellow300)
                )
            ), horizontalExtraSpace = 0.dp, isZoomAllowed = false,
            selection = LinePlot.Selection(enabled = false),
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
                roundToInt = sensorType != "pH",
                steps = 5,
                paddingStart = yAxisPadding,
                paddingEnd = yAxisPadding,
                content = { min, offset, _ ->
                    for (it in 0 until 5) {
                        val value = it * offset + min
                        Text(
                            text = value.string(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(yAxisWidth)
                        )
                    }
                })
        ),
        modifier
            .onGloballyPositioned {
                totalWidthOfChart.value =
                    it.size.width.toFloat().toDp(density).toInt() - yAxisTotalWidth.toInt()
            },
    )
}

/**THE GRAPH PROVIDED BY USED LIBRARY IS INTENDED TO BE HORIZONTALLY SCROLLABLE, HOWEVER IN THIS APP THIS BEHAVIOUR ISN'T BENEFICIAL
 * THEREFORE APP CALCULATES LAST VISIBLE POINT ON X AXIS FOR DIFFERENT SCREEN SIZES.
 * */
private fun lastVisiblePointForScreenWidth(totalWidth: Int): Float {
    val screenWidthDivider = 720.0
    val multiplier = 36
    return (totalWidth * multiplier / screenWidthDivider).toFloat()
}

/** TRANSFORMS THE GIVEN LIST OF DATA POINTS INTO THE ACCURATE LIST OF RATIOS AND VALUES.
 *  [graphWidth] IS IMPORTANT BECAUSE DIFFERENT SCREENS CAN SHOW DIFFERENT AMOUNT OF DATA POINTS.
 * */
private fun transformList(list: List<DataPoint>, graphWidth: Float): List<DataPoint> {
    val listOfRatios = computeListOfXValuesAsRatios(list, graphWidth)
    val newList: MutableList<DataPoint> = mutableListOf()
    for (dataPoint in list) {
        val newDataPoint =
            DataPoint(listOfRatios[list.indexOf(dataPoint)], list[list.indexOf(dataPoint)].y)
        newList.add(newDataPoint)
    }
    return newList
}

/**The list of farm data is containing values and dates, dates are not recognized by graph. Therefore the implemented solution is to transform the dates into
 * epoch seconds and then turn them into list of ratios so the data on chart is drawn exactly how it is supposed to be, with irregular gaps.*/
private fun computeListOfXValuesAsRatios(
    list: List<DataPoint>,
    lastVisiblePoint: Float
): List<Float> {
    val zeroValue = list.first().x
    val listOfValues = list.map { it.x - zeroValue }
    val lastValue = listOfValues.last()
    return listOfValues.map { (it * lastVisiblePoint / lastValue) }
}


private fun Float.string() = DecimalFormat("#.#").format(this)

/**The larger the difference between the smallest and biggest Farm Data value the more Lines on the Y Grid
 * If the difference is smaller than 2 the number after decimal point is taken into account. */
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