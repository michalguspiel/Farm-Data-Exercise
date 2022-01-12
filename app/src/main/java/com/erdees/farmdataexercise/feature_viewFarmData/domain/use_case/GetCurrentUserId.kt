package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.domain.repository.AuthRepository

class GetCurrentUserId(private val repository: AuthRepository) {

    operator fun invoke() = repository.getCurrentUserId()
}