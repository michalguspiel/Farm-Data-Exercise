package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Screen
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.ISO8601.formatDate
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.*
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDate

@InternalCoroutinesApi
@Composable
fun SelectFarmDataScreen(viewModel: FarmDataViewModel = hiltViewModel(), navController: NavController) {

    var chosenFarm by remember {
        mutableStateOf("")
    }

    var sensorType by remember {
        mutableStateOf("")
    }

    var timeRange by remember {
        mutableStateOf(listOf<LocalDate>())
    }

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
        when (val additionResponse = viewModel.isFarmDataAddedState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> Toast(stringResource(R.string.data_added_successfully))
            is Error -> Toast(additionResponse.message)
        }
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.choose_farm), fontSize = 26.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Spinner(
            onValueChange = { chosenFarm = it },
            textToPresent = stringResource(id = R.string.sensorType),
            list = Constants.FARM_LIST,
            modifier = Modifier.padding(horizontal = 40.dp),
            Color.Gray,
            Color.LightGray
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = stringResource(R.string.choose_time_range), fontSize = 20.sp)
        Calendar(modifier = Modifier.padding(horizontal = 30.dp),calendarState = rememberSelectionState(
            initialSelectionMode = SelectionMode.Period,
            selectionState = CustomSelectionState({
                timeRange = if(it.isNotEmpty()) listOf(it.first(), it.last()) else listOf()
                Log.i("SelectFarmDataScreen",timeRange.joinToString(",") { it.toString() })
            }, selectionMode = SelectionMode.Period, selection = timeRange)
        ),monthHeader = { CalendarHeader(it) })
        Text(text = stringResource(R.string.choose_metric), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Spinner(
            onValueChange = { sensorType = it },
            textToPresent = stringResource(id = R.string.sensorType),
            list = Constants.SENSOR_LIST,
            modifier = Modifier.padding(horizontal = 40.dp),
            Color.Gray,
            Color.LightGray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {Log.i("TAG",Screen.FarmDataScreen.withArgs(chosenFarm,sensorType,formatDate(timeRange.first()),formatDate(timeRange[1])) )
            navController.navigate(Screen.FarmDataScreen.withArgs(chosenFarm,sensorType,formatDate(timeRange.first()),formatDate(timeRange[1])))
                         },
            enabled = !(chosenFarm == "" || sensorType == "" || timeRange.isEmpty())
        ) {
            Text(text = stringResource(R.string.show_data))
        }

    }
}
