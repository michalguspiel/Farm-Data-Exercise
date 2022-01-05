package com.erdees.farmdataexercise.feature_auth.domain.use_case

import com.erdees.farmdataexercise.feature_auth.domain.repository.AuthRepository

class IsUserAuthenticated(private val repository: AuthRepository) {

    operator fun invoke() = repository.isUserAuthenticated()
}