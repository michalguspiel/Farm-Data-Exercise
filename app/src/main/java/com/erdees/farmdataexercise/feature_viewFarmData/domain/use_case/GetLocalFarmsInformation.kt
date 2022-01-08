package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmInfoRepository

class GetLocalFarmsInformation(private val repository: FarmInfoRepository) {

    suspend operator fun invoke() = repository.getLocalFarmsInformation()
}