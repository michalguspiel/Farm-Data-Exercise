package com.erdees.farmdataexercise.feature_auth.domain.use_case

import com.erdees.farmdataexercise.feature_auth.domain.repository.AuthRepository

class SignInWithEmail(private val repository: AuthRepository) {


    suspend operator fun invoke(email: String, password: String) = repository.signInWithEmail(email,password)
}