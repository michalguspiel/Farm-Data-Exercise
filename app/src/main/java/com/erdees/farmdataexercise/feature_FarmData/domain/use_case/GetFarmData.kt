package com.erdees.farmdataexercise.feature_FarmData.domain.use_case

import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmDataRepository

class GetFarmData(private val repository: FarmDataRepository) {
    operator fun invoke(
        farmLocation: String,
        sensorType: String,
        rangeFirst: String,
        rangeSecond: String
    ) = repository.getFarmData(farmLocation, sensorType, rangeFirst, rangeSecond)
}