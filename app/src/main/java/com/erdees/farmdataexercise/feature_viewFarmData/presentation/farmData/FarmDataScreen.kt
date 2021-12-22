package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData


import android.util.Log
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
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.*
import com.erdees.farmdataexercise.ui.theme.Typography
import com.madrapps.plot.line.DataPoint
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
    Log.i("TAG","$location , $sensorType , $rangeFirst , $rangeSecond")
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
            is Response.Success -> Column(
                modifier = Modifier.fillMaxSize().padding(top = 12.dp)
            ) {
                if(farmDataResponse.data.isEmpty()){
                    Text(text = "Ooops... looks like there is no data in that time.",modifier = Modifier.fillMaxSize(),style = Typography.h3)}
                else{
                Text(text = farmDataResponse.data.first().location, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth(), style = Typography.h4)
                val lines = farmDataResponse.data.map {
                    DataPoint(
                        Format.formatToSeconds(farmDataResponse.data[farmDataResponse.data.indexOf(it)].datetime).toFloat(),
                        it.value.toFloat())
                }
                CustomLineGraph(lines = lines,farmDataResponse.data.map { it.datetime },farmDataResponse.data.first().sensorType)
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
