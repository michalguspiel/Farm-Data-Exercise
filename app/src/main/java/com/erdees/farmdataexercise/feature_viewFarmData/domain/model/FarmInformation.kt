package com.erdees.farmdataexercise.feature_viewFarmData.domain.model

import com.erdees.farmdataexercise.feature_viewFarmData.data.local.FarmInformationEntity
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

data class FarmInformation(
    val locationName :String = "",
    val geoPoint: GeoPoint = GeoPoint(0.0,0.0),
    @DocumentId
    val docId: String = ""
) {

    fun toFarmInformationEntity() = FarmInformationEntity(locationName = locationName,geoPoint =  geoPoint, docId = docId)
}