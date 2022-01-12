package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.domain.repository.AuthRepository

class IsUserAuthenticated(private val repository: AuthRepository) {

    operator fun invoke() = repository.isUserAuthenticated()
}