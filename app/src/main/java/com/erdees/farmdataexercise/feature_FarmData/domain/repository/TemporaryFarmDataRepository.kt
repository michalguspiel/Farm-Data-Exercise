package com.erdees.farmdataexercise.feature_FarmData.domain.repository

import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import kotlinx.coroutines.flow.StateFlow

interface TemporaryFarmDataRepository {

    fun getFarmData(): StateFlow<List<FarmData>>

    fun saveFarmData(farmDataList: List<FarmData>)


}