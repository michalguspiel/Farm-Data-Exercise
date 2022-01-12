package com.erdees.farmdataexercise.feature_auth.domain.use_case

import com.erdees.farmdataexercise.domain.repository.AuthRepository

class GetCurrentUserDocument(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.getCurrentUserDocument()
}