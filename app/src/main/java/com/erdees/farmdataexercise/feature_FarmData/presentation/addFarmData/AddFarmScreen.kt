package com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData.components.AddFarmContent
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.Toast
import com.erdees.farmdataexercise.model.Response
import java.util.*

@Composable
fun AddFarmScreen(
    viewModel: AddFarmScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    Scaffold(
        topBar = {
            MyTopAppBar(screen = Screen.AddFarmDataScreen, navController = navController)
        }
    ) {
        AddFarmContent()

        when (val additionResponse = viewModel.isFarmDataAddedState.value) {
            is Response.Empty -> {
            }
            is Response.Loading -> ProgressBar()
            is Response.Success ->{
                Toast(stringResource(R.string.data_added_successfully))
                viewModel.dataValue.value = ""
            }
            is Error -> Toast("Data input invalid!")
        }

            if (viewModel.isDatePickerShown.value) {
                showDatePicker(context = context, viewModel)
            }

            if (viewModel.isTimePickerShown.value) {
                showTimePicker(
                    context = context,
                    initHour = hour,
                    initMinute = minute,
                    viewModel = viewModel
                )
            }
        }
    }

fun showDatePicker(context: Context, viewModel: AddFarmScreenViewModel) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(context, { _, y, m, d ->
        viewModel.pickDate(y, m, d)
    }, year, month, day)
        datePickerDialog.setOnCancelListener {
            viewModel.isDatePickerShown.value = false
        }
    datePickerDialog.show()

}

fun showTimePicker(
    context: Context,
    initHour: Int,
    initMinute: Int,
    viewModel: AddFarmScreenViewModel
) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            viewModel.pickTime(hour, minute)
        }, initHour, initMinute, true
    )
    timePickerDialog.setOnCancelListener {
        viewModel.isTimePickerShown.value = false
    }
    timePickerDialog.show()
}