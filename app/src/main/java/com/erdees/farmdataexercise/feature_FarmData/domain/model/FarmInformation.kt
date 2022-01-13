package com.erdees.farmdataexercise.feature_FarmData.domain.model

import com.erdees.farmdataexercise.feature_FarmData.data.local.FarmInformationEntity
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

data class FarmInformation(
    val locationName :String = "",
    val geoPoint: GeoPoint = GeoPoint(0.0,0.0),
    val farmOwnerId: String = "",
    @DocumentId
    val docId: String = "",
    val farmImageUrl : String = ""
) {

    fun toFarmInformationEntity() = FarmInformationEntity(locationName = locationName,geoPoint =  geoPoint, docId = docId,farmImageUrl = farmImageUrl, farmOwnerId = farmOwnerId)
}