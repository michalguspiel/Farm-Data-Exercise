package com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
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


    var pickedDate = mutableStateOf(LocalDate.of(0,1,1))

    var pickedTime = mutableStateOf(Pair(0,0))

    var sensorTypeDocument = mutableStateOf("")

    var sensorTypeName = mutableStateOf("")

    var dataValue = mutableStateOf("")

    fun pickTime(hour: Int, minute: Int) {
        pickedTime.value = Pair(hour,minute)
        isTimePickerShown.value = false
    }

    fun pickDate(y: Int, m: Int, d: Int) {
        val localDate =  LocalDate.of(y,m+1,d)
        pickedDate.value = localDate
        isDatePickerShown.value = false
    }

    private val pickedDataTime
        get() : String {
           return Format.formatDateAndTimeToISO8601(pickedDate.value,pickedTime.value.first,pickedTime.value.second)
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