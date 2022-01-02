package com.erdees.farmdataexercise.feature_viewFarmData.domain.repository

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import kotlinx.coroutines.flow.StateFlow

interface TemporaryFarmDataRepository {

    fun getFarmData(): StateFlow<List<FarmData>>

    fun saveFarmData(farmDataList: List<FarmData>)


}