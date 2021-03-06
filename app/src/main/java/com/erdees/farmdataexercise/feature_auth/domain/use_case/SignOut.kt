package com.erdees.farmdataexercise.feature_auth.domain.use_case

import com.erdees.farmdataexercise.domain.repository.AuthRepository

class SignOut(private val repository: AuthRepository) {

    suspend operator fun invoke() = repository.signOut()
}