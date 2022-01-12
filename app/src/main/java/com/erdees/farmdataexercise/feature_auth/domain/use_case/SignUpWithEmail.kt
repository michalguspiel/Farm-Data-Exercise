package com.erdees.farmdataexercise.feature_auth.domain.use_case

import com.erdees.farmdataexercise.domain.repository.AuthRepository
import com.erdees.farmdataexercise.feature_auth.domain.util.Registration

class SignUpWithEmail(private val repository: AuthRepository) {

    suspend operator fun invoke(registration : Registration) = repository.signUpWithEmail(registration)
}