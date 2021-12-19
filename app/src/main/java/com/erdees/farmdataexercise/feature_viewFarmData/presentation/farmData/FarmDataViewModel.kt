package com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmDataViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _farmDataState = mutableStateOf<Response<List<FarmData>>>(Response.Loading)
    val farmDataState: State<Response<List<FarmData>>> = _farmDataState

    private val _isFarmDataAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isFarmDataAddedState: State<Response<Void?>> = _isFarmDataAddedState
    var openDialogState = mutableStateOf(false)




     fun getFarmData(farmLocation: String) {
        viewModelScope.launch {
            useCases.getFarmData.invoke(farmLocation).collect { response ->
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
        viewModelScope.launch{
            useCases.addFarmData.invoke(farmLocation,dateTime, sensorType, value).collect {  response ->
                _isFarmDataAddedState.value = response
            }
        }
    }

}