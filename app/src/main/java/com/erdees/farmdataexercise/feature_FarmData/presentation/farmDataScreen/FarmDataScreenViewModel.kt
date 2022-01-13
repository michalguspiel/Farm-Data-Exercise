package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import com.erdees.farmdataexercise.model.Response
import com.madrapps.plot.line.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FarmDataScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _farmDataState = mutableStateOf<Response<List<FarmData>>>(Response.Loading)
    val farmDataState: State<Response<List<FarmData>>> = _farmDataState

    private val _isFarmDataAddedState = mutableStateOf<Response<Void?>>(Response.Empty(null))
    val isFarmDataAddedState: State<Response<Void?>> = _isFarmDataAddedState

    var openDialogState = mutableStateOf(false)

    var isGraphShown = mutableStateOf(true)

    fun graphStateChanged(){
        isGraphShown.value = isGraphShown.value.not()
    }

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

    fun getLines():List<DataPoint>{
        return when(val farmDataState = farmDataState.value){
            is Response.Success -> farmDataState.data.map {
                DataPoint(
                    Format.formatToSeconds(
                        farmDataState.data[farmDataState.data.indexOf(
                            it
                        )].datetime
                    ).toFloat(),
                    it.value.toFloat()
                )
            }
            else -> listOf()
        }
    }

    fun saveTemporaryFarmData(farmDataList: List<FarmData>) {
        useCases.saveTemporaryFarmData.invoke(farmDataList)
    }

}