package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository
import kotlinx.coroutines.flow.StateFlow

class GetTemporaryFarmData(private val repositoryImpl: TemporaryFarmDataRepository) {

    operator fun invoke(): StateFlow<List<FarmData>> = repositoryImpl.getFarmData()
}