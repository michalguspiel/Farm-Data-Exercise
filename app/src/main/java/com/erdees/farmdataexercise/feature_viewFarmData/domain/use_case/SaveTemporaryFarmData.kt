package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository

class SaveTemporaryFarmData(private val repositoryImpl: TemporaryFarmDataRepository){

    operator fun invoke(farmDataList: List<FarmData>) = repositoryImpl.saveFarmData(farmDataList)
}