package com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.firebase.firestore.GeoPoint
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

    private val _farmAdditionState = mutableStateOf<Response<Void?>>(Response.Empty(null))
    val farmAdditionState : State<Response<Void?>> = _farmAdditionState

    val isUserAuthenticated get() = useCases.isUserAuthenticated()

     val currentUserId get() = useCases.getCurrentUserId()

    val isOpenDialogState : MutableState<Boolean> = mutableStateOf(false)

    var isAddFarmButtonShownState = mutableStateOf(false)

    var clickedLatLngState : MutableState<LatLng> = mutableStateOf(LatLng(0.0,0.0))


    /**List which allows to keep track which FarmInformation
     *  is being displayed as a Window adapter on a map.
     *
     *  It could be limited to saving only data about marker and FarmInformation docId,
     *  however I think the code clarity is better like this.
     *  */
    private val markerTagMap = mutableListOf<Pair<String,FarmInformation>>()


    fun addPairToMap(pair : Pair<String,FarmInformation>){
        val pairInMap = markerTagMap.find { it.first == pair.first }
       if(pairInMap == null) markerTagMap.add(pair)
    }

    fun selectFarm(marker: Marker) {
        markerTagMap.forEach {
        }
        val pair = markerTagMap.find { it.first == marker.tag }
        if (pair != null) {
            selectFarm(
                pair.second
            )
        }
    }

    fun clearSelectedFarm(){
        selectFarm(null)
    }

    fun selectFarm(farmInformation: FarmInformation?){
        _selectedFarmState.value = farmInformation
    }

     fun getLocalFarmsInformation() {
         viewModelScope.launch{
             useCases.getLocalFarmsInformation.invoke().collect{ response ->
                 _farmsInformationState.value = response
                 Log.i("SelectFarm", "$response.")
             }
         }
     }


    fun resetFarmAdditionResponse(){
        _farmAdditionState.value = Response.Empty(null)
    }

    fun addFarm(farmName : String){
        val geoPoint = GeoPoint(clickedLatLngState.value.latitude,clickedLatLngState.value.longitude)

        viewModelScope.launch{
            useCases.addFarm.invoke(farmName,geoPoint,currentUserId!!).collect { response ->
                _farmAdditionState.value = response
            }
        }
    }

}