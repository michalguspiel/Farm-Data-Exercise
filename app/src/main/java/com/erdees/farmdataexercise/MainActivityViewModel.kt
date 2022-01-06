package com.erdees.farmdataexercise

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class MainActivityViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _farmInformationState =
        mutableStateOf<Response<List<FarmInformation>>>(Response.Loading)
    private val farmInformationState: State<Response<List<FarmInformation>>> = _farmInformationState

    init {
        getFarmsInformation()
    }

    fun isLoading() = farmInformationState.value == Response.Loading

    private fun getFarmsInformation() {
        viewModelScope.launch {
            useCases.getFarmsInformation.invoke().collect { response ->
                _farmInformationState.value = response
            }
        }
    }
}