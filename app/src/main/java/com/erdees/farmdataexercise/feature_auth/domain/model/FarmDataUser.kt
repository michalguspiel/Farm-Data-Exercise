package com.erdees.farmdataexercise.feature_auth.domain.model

data class FarmDataUser(
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val accessedFarms: List<String> = listOf(),
) {
}