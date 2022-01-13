package com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format.formatStringToDatabaseFormat
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFarmScreenViewModel @Inject constructor(
    val useCases: UseCases,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _isFarmDataAddedState = mutableStateOf<Response<Void?>>(Response.Empty(null))
    val isFarmDataAddedState: State<Response<Void?>> = _isFarmDataAddedState


    var isDatePickerShown = mutableStateOf(false)
    var isTimePickerShown = mutableStateOf(false)


    var pickedDate = mutableStateOf("")

    var pickedTime = mutableStateOf("")

    var sensorTypeDocument = mutableStateOf("")

    var sensorTypeName = mutableStateOf("")

    var dataValue = mutableStateOf("")

    fun pickTime(hour: Int, minute: Int) {
        val timeFormatted = "$hour:$minute"//:00.000"
        pickedTime.value = timeFormatted
        isTimePickerShown.value = false
    }

    fun pickDate(y: Int, m: Int, d: Int) {
        val formattedDate = "$y-${m + 1}-$d"
        pickedDate.value = formattedDate
        isDatePickerShown.value = false
    }

    private val pickedDataTime
        @SuppressLint("SimpleDateFormat")
        get() : String {
           val ownFormat = pickedDate.value + "T" + pickedTime.value + ":00.000Z"
           return formatStringToDatabaseFormat(ownFormat)
        }

    fun addFarmData(
    ) {
        viewModelScope.launch {
            useCases.addFarmData.invoke(savedStateHandle.get<String>(Constants.LOCATION_DOC_ID)!!, pickedDataTime, sensorTypeDocument.value, dataValue.value)
                .collect { response ->
                    _isFarmDataAddedState.value = response
                }
        }
    }

}