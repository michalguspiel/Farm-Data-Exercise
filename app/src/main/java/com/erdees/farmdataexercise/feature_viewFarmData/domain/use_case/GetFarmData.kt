package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmDataRepository

class GetFarmData(private val repository: FarmDataRepository) {
    suspend operator fun invoke(
        farmLocation: String,
        sensorType: String,
        rangeFirst: String,
        rangeSecond: String
    ) = repository.getFarmData(farmLocation, sensorType, rangeFirst, rangeSecond)
}