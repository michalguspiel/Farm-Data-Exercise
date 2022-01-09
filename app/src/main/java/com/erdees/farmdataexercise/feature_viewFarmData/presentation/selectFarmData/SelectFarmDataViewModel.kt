package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarmData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFarmDataViewModel @Inject constructor(
    private val useCases: UseCases,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _farmDataState = mutableStateOf<Response<List<FarmData>>>(Response.Loading)
    val farmDataState: State<Response<List<FarmData>>> = _farmDataState

    private val _isFarmDataAddedState = mutableStateOf<Response<Void?>>(Response.Empty(null))
    val isFarmDataAddedState: State<Response<Void?>> = _isFarmDataAddedState

    var openDialogState = mutableStateOf(false)

    fun getFarmData() {
        viewModelScope.launch {
            useCases.getFarmData.invoke(
                savedStateHandle.get<String>(Constants.LOCATION_DOC_ID).toString(),
                savedStateHandle.get<String>(Constants.SENSOR_TYPE).toString(),
                savedStateHandle.get<String>(Constants.RANGE_FIRST).toString(),
                savedStateHandle.get<String>(Constants.RANGE_SECOND).toString(),
            ).collect { response ->
                _farmDataState.value = response
            }
        }
    }


    fun addFarmData(
        farmLocation: String,
        dateTime: String,
        sensorType: String,
        value: String
    ) {
        viewModelScope.launch {
            useCases.addFarmData.invoke(farmLocation, dateTime, sensorType, value)
                .collect { response ->
                    _isFarmDataAddedState.value = response
                }
        }
    }



}