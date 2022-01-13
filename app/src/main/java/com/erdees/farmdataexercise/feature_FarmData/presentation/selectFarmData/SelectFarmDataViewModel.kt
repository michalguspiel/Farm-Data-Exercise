package com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarmData

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectFarmDataViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
}