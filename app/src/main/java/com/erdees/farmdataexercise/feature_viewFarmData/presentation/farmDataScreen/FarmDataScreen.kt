package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmDataScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatDateToYearMonthDay
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.AlertDialog
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.CustomPreviewLineGraph
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun FarmDataScreen(
    viewModel: FarmDataScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    viewModel.getFarmData()

    val timeRange = viewModel.savedStateHandle.get<String>(Constants.RANGE_FIRST).toString()
    val timeRange2 = viewModel.savedStateHandle.get<String>(Constants.RANGE_SECOND).toString()
    val sensorType = viewModel.savedStateHandle.get<String>(SENSOR_NAME).toString()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.openDialogState.value = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.add_farm_data
                    )
                )
            }
        }
    ) {
        if (viewModel.openDialogState.value) {
            AlertDialog()
        }
        when (val farmDataResponse = viewModel.farmDataState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
            ) {
                if (farmDataResponse.data.isEmpty()) {
                    Text(
                        text = "Ooops... looks like there is no data in that time.",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        style = Typography.h4
                    )
                } else {
                    Text(
                        text = farmDataResponse.data.first().location,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.h5
                    )
                    Text(
                        text = sensorType,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.h3
                    )
                    Text(
                        text = "Time range: ${formatDateToYearMonthDay(timeRange)} - ${
                            formatDateToYearMonthDay(
                                timeRange2
                            )
                        }",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.body2
                    )

                    Spacer(modifier = Modifier.height(4.dp))


                    Box(modifier = Modifier.clickable(onClick = {
                        viewModel.saveTemporaryFarmData(farmDataResponse.data)
                        navController.navigate(
                            Screen.DetailedFarmDataGraphScreen.withArgs(
                                viewModel.savedStateHandle.get<String>(Constants.LOCATION_NAME)
                                    .toString(),
                                viewModel.savedStateHandle.get<String>(Constants.SENSOR_NAME)
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
                    DataTable(farmDataResponse.data)

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
            .padding(horizontal = 4.dp, vertical = 2.dp),
        color = textColor,
    )
}

@Composable
fun DataRow(farmData: FarmData) {
    Row(Modifier.background(BackgroundColor)) {
        TableCell(text = farmData.datetime, weight = 1f)
        TableCell(text = farmData.value, weight = 1f)
    }
}

@Composable
fun DataTable(farmDataList: List<FarmData>) {
    LazyColumn {
        item {
            Row(Modifier.background(BackgroundColorDarkest)) {
                TableCell(text = "Date time", weight = 1f, style = Typography.h6, OnPrimary)
                TableCell(text = "Value", weight = 1f, style = Typography.h6, OnPrimary)
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
        )
    )
}
