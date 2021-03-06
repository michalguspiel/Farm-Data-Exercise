package com.erdees.farmdataexercise.feature_FarmData.presentation.detailedFarmDataGraphScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailedFarmDataGraphScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    fun getTemporaryFarmData(): StateFlow<List<FarmData>> = useCases.getTemporaryFarmData.invoke()

}