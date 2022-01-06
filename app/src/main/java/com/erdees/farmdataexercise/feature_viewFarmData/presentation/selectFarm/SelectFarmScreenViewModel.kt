package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
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

     fun getLocalFarmsInformation() {
         viewModelScope.launch{
             useCases.getLocalFarmsInformation.invoke().collect{ response ->
                 _farmsInformationState.value = response
             }
         }
     }

}