package com.erdees.farmdataexercise.feature_viewFarmData.domain.model

interface SensorType {

    val firebaseName : String

    val presentationName: String

}

class Temperature : SensorType {

    override val firebaseName: String
        get() = "temperature"

    override val presentationName: String
        get() = "Temperature"
}

class PH : SensorType{
    override val firebaseName: String
        get() = "pH"

    override val presentationName: String
        get() = "pH"
}

class Rainfall : SensorType {
    override val firebaseName: String
        get() = "rainFall"

    override val presentationName: String
        get() = "Rain fall"
}

data class SensorTypes(
    val temperature: Temperature,
    val rainFall: Rainfall,
    val pH: PH
)