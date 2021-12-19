package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.AlertDialog
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.FarmDataCard
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun FarmDataScreen(
    location: String?,
    sensorType : String?,
    rangeFirst: String?,
    rangeSecond: String?,
    viewModel: FarmDataViewModel = hiltViewModel()
) {
    viewModel.getFarmData(location!!,sensorType!!,rangeFirst!!,rangeSecond!!)
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
            is Response.Success -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    items(farmDataResponse.data) { farmData ->
                        FarmDataCard(

                        )
                    }
                }
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
            is Error -> Toast(farmDataResponse.message)
        }

    }

}
