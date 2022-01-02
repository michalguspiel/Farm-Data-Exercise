package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.AlertDialog
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.CustomPreviewLineGraph
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.ui.theme.Typography
import com.madrapps.plot.line.DataPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun FarmDataScreen(
    viewModel: FarmDataViewModel = hiltViewModel(),
    navController: NavController,
) {
    viewModel.getFarmData()
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
                        modifier = Modifier.fillMaxSize()
                            .clickable(onClick = { // TODO TO DELETE AFTER TESTING
                                viewModel.saveTemporaryFarmData(farmDataResponse.data)
                                navController.navigate(
                                    Screen.DetailedFarmDataGraphScreen.route
                                )
                            }),
                        style = Typography.h3
                    )
                } else {
                    Text(
                        text = farmDataResponse.data.first().location,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.h4
                    )
                    val lines = farmDataResponse.data.map {
                        DataPoint(
                            Format.formatToSeconds(
                                farmDataResponse.data[farmDataResponse.data.indexOf(
                                    it
                                )].datetime
                            ).toFloat(),
                            it.value.toFloat()
                        )
                    }
                    Box(modifier = Modifier.clickable(onClick = {
                        viewModel.saveTemporaryFarmData(farmDataResponse.data)
                        navController.navigate(
                            Screen.DetailedFarmDataGraphScreen.route
                        )
                    }))
                    {
                        CustomPreviewLineGraph(
                            lines = lines,
                            farmDataResponse.data.map { it.datetime },
                            farmDataResponse.data.first().sensorType,
                            Modifier.fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                            Box (
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
