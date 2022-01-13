package com.erdees.farmdataexercise.feature_FarmData.domain.use_case

import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.TemporaryFarmDataRepository

class SaveTemporaryFarmData(private val repositoryImpl: TemporaryFarmDataRepository){

    operator fun invoke(farmDataList: List<FarmData>) = repositoryImpl.saveFarmData(farmDataList)
}