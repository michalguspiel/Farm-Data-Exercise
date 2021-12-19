package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import android.widget.Spinner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_LIST
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.FarmDataViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun AlertDialog(viewModel: FarmDataViewModel = hiltViewModel()) {

    var dateTime by remember {
        mutableStateOf("")
    }

    var sensorType by remember{
        mutableStateOf("")
    }

    var dataValue by remember{
        mutableStateOf("")
    }

    val focusRequester = FocusRequester()

    if (viewModel.openDialogState.value) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {
                viewModel.openDialogState.value = false
            },
            title = {
                Text(text = stringResource(com.erdees.farmdataexercise.R.string.add_farm_data))
            },
            text = {
                Column {
                    Spinner(
                        onValueChange = { sensorType = it },
                        textToPresent = stringResource(id = com.erdees.farmdataexercise.R.string.sensorType ),
                        list = SENSOR_LIST,
                        modifier = Modifier.padding(horizontal = 40.dp),
                        Color.Gray,
                        Color.LightGray

                    )
                    //DisposableEffect(Unit) {
                  //      focusRequester.requestFocus()
                   //     onDispose { }
                  //  }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = dataValue,
                        onValueChange = { dataValue = it },
                        placeholder = { Text(stringResource(id = com.erdees.farmdataexercise.R.string.value)) }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false
                        viewModel.addFarmData(Constants.NOORA_FARM,"NOW", sensorType,dataValue)
                    }
                ) {
                    Text(stringResource(id = com.erdees.farmdataexercise.R.string.add))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false
                    }
                ) {
                    Text(stringResource(id = com.erdees.farmdataexercise.R.string.cancel))
                }
            }
        )
    }
}