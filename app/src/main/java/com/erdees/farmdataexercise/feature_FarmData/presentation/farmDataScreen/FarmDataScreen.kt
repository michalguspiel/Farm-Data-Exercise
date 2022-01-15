package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_NAME
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.CustomPreviewLineGraph
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.Toast
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components.DataTable
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components.FarmDataInformationCard
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components.NoDataContent
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components.OneDataContent
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
                }
                else if(farmDataResponse.data.size == 1) {
                    OneDataContent(farmDataResponse.data.first(),navController)
                }
                    else
                 {
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
