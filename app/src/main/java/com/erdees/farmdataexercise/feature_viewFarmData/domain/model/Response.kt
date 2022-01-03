package com.erdees.farmdataexercise.feature_viewFarmData.domain.model

sealed class Response<out T> {
    object Loading: Response<Nothing>()


    data class Empty<out T>(val data: T): Response<T>()


    data class Success<out T>(
        val data: T
    ): Response<T>()


    data class Error(
        val message: String
    ): Response<Nothing>()
}