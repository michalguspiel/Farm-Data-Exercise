package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmDataScreen


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_NAME
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Temperature
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatDateToYearMonthDay
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatISO8601String
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatTemperature
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.CustomPreviewLineGraph
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun FarmDataScreen(
    viewModel: FarmDataScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    viewModel.getFarmData()

    val locationName = viewModel.savedStateHandle.get<String>(Constants.LOCATION_NAME).toString()
    val timeRange = viewModel.savedStateHandle.get<String>(Constants.RANGE_FIRST).toString()
    val timeRange2 = viewModel.savedStateHandle.get<String>(Constants.RANGE_SECOND).toString()
    val sensorType = viewModel.savedStateHandle.get<String>(SENSOR_NAME).toString()


    Scaffold {
        when (val farmDataResponse = viewModel.farmDataState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> Column(
                modifier = Modifier
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (farmDataResponse.data.isEmpty()) {
                    NoDataContent(navController = navController)
                } else {
                    FarmDataInformationCard(
                        locationName = locationName,
                        sensorType = sensorType,
                        timeRange = timeRange,
                        timeRange2 = timeRange2
                    )
                    Divider(color = OnPrimary, thickness = 1.dp)

                    Card(
                        shape = RectangleShape,
                        modifier = Modifier,
                        onClick = { viewModel.graphStateChanged() },
                        elevation = LocalElevation.current.default
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    BackgroundColorDarker
                                )
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (viewModel.isGraphShown.value) stringResource(R.string.hide_graph) else stringResource(
                                    R.string.show_graph
                                ), textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(
                                        horizontal = LocalSpacing.current.default,
                                        vertical = LocalSpacing.current.xxSmall
                                    )
                                    .width(120.dp),
                                style = Typography.h6
                            )
                            Icon(
                                imageVector = if (viewModel.isGraphShown.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = stringResource(id = R.string.expand_icon),
                                modifier = Modifier
                                    .padding(horizontal = LocalSpacing.current.default, vertical = LocalSpacing.current.xxSmall)
                            )
                        }
                    }

                    androidx.compose.animation.AnimatedVisibility(
                        viewModel.isGraphShown.value,
                        modifier = Modifier,
                        enter = expandVertically(
                            expandFrom = Alignment.Top,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMedium,
                                dampingRatio = Spring.DampingRatioLowBouncy
                            ),
                        ),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Top,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMedium,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                    ) {
                        Box(modifier = Modifier.clickable(onClick = {
                            viewModel.saveTemporaryFarmData(farmDataResponse.data)
                            navController.navigate(
                                Screen.DetailedFarmDataGraphScreen.withArgs(
                                    viewModel.savedStateHandle.get<String>(Constants.LOCATION_NAME)
                                        .toString(),
                                    viewModel.savedStateHandle.get<String>(SENSOR_NAME)
                                        .toString(),
                                )
                            )
                        }))
                        {
                            CustomPreviewLineGraph(
                                lines = viewModel.getLines(),
                                farmDataResponse.data.map { it.datetime },
                                farmDataResponse.data.first().sensorType,
                                Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                    }
                    DataTable(
                        farmDataResponse.data,
                        modifier = Modifier.padding(horizontal = LocalSpacing.current.medium)
                    )

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (val additionResponse = viewModel.isFarmDataAddedState.value) {
                            is Response.Loading -> CircularProgressIndicator()
                            is Response.Success -> Unit
                            is Error -> Toast(additionResponse.message)
                        }
                    }
                }
            }
            is Error -> Toast(farmDataResponse.message)
        }
    }

}

@Composable
fun NoDataContent(navController: NavController) {
    Spacer(Modifier.height(LocalSpacing.current.xLarge))
    Text(
        text = stringResource(id = R.string.oops_no_data),
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.default),
        textAlign = TextAlign.Center,
        style = Typography.h4
    )
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
    MyButton(onClick = { navController.popBackStack() }, text = stringResource(id = R.string.back))

}

@Composable
fun FarmDataInformationCard(
    locationName: String,
    sensorType: String,
    timeRange: String,
    timeRange2: String
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.background(BackgroundColor),
        elevation = LocalElevation.current.default
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = locationName,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.h4
            )
            Text(
                text = sensorType,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.h5
            )
            Divider(color = OnPrimary, thickness = 1.dp)
            Text(
                text = "Time range: ${formatDateToYearMonthDay(timeRange)} - ${
                    formatDateToYearMonthDay(
                        timeRange2
                    )
                }",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor),
                style = Typography.body1
            )
        }
    }
}

@Preview
@Composable
fun PreviewFarmDataInfoCard() {
    FarmDataInformationCard(
        locationName = "Michal farm",
        sensorType = "Ph",
        timeRange = "09.01.2021",
        timeRange2 = "12.01.2022"
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle = Typography.body2,
    textColor: Color = OnPrimaryLightest
) {
    Text(
        text = text, style = style,
        modifier = Modifier
            .border(1.dp, Green400)
            .weight(weight)
            .padding(
                horizontal = LocalSpacing.current.xSmall,
                vertical = LocalSpacing.current.xxSmall
            ),
        color = textColor,
    )
}

@Composable
fun DataRow(farmData: FarmData) {
    val farmDataValue =
        if (farmData.sensorType == Temperature().firebaseName) formatTemperature(farmData.value) else farmData.value
    Row(Modifier.background(BackgroundColor)) {
        TableCell(text = formatISO8601String(farmData.datetime), weight = 0.7f)
        TableCell(text = farmDataValue, weight = 0.3f)
    }
}

@Composable
fun DataTable(farmDataList: List<FarmData>, modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(bottom = LocalSpacing.current.default)) {
        item {
            Row(Modifier.background(BackgroundColorDarkest)) {
                TableCell(
                    text = stringResource(R.string.date_time),
                    weight = 0.7f,
                    style = Typography.h6,
                    OnPrimary
                )
                TableCell(
                    text = stringResource(R.string.value),
                    weight = 0.3f,
                    style = Typography.h6,
                    OnPrimary
                )
            }
        }
        items(farmDataList) { farmData ->
            DataRow(farmData = farmData)
        }

    }
}

@Composable
@Preview
fun PreviewDataTable() {
    DataTable(
        listOf(
            FarmData("Location", "20:20:20:20", "", "13"),
            FarmData("Location", "20:20:20:20", "", "13"),
            FarmData("Location", "20:20:20:20", "", "13"),
            FarmData("Location", "20:20:20:20", "", "13"),
            FarmData("Location", "20:20:20:20", "", "13"),
        ), Modifier
    )
}
