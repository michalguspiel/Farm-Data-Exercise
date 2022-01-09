package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
import com.google.android.libraries.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectFarmScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _farmsInformationState = mutableStateOf<Response<List<FarmInformation>>>(Response.Empty(listOf()))
    val farmsInformationState: State<Response<List<FarmInformation>>> = _farmsInformationState

    private val _selectedFarmState = mutableStateOf<FarmInformation?>(null)
    val selectedFarmState :State<FarmInformation?> = _selectedFarmState


    /**List which allows to keep track which FarmInformation
     *  is being displayed as a Window adapter on a map.
     *
     *  It could be limited to saving only data about marker and FarmInformation docId,
     *  however I think the code clarity is better like this.
     *  */
    val markerMap = mutableListOf<Pair<Marker,FarmInformation>>()

    fun selectFarm(marker: Marker) {
        val pair = markerMap.find { it.first == marker }
        if (pair != null) {
            selectFarm(
                pair.second
            )
        }
    }

    fun clearSelectedFarm(){
        selectFarm(null)
    }

    private fun selectFarm(farmInformation: FarmInformation?){
        _selectedFarmState.value = farmInformation
    }

     fun getLocalFarmsInformation() {
         viewModelScope.launch{
             useCases.getLocalFarmsInformation.invoke().collect{ response ->
                 _farmsInformationState.value = response
             }
         }
     }

}