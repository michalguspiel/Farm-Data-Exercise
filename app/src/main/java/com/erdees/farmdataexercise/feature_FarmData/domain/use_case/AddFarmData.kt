package com.erdees.farmdataexercise.feature_FarmData.domain.use_case

import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmDataRepository

class AddFarmData(private val farmDataRepository: FarmDataRepository) {
    suspend operator fun invoke(
        locationName: String,
        dateTime: String,
        sensorType: String,
        value: String
    ) = farmDataRepository.addFarmData(locationName,dateTime,sensorType,value)

}