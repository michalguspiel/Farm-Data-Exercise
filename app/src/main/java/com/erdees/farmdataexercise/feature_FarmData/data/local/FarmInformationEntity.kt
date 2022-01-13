package com.erdees.farmdataexercise.feature_FarmData.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmInformation
import com.google.firebase.firestore.GeoPoint

@Entity
class FarmInformationEntity(
    val locationName: String,
    @TypeConverters(GeoPointConverter::class) val geoPoint: GeoPoint,
    val farmImageUrl: String,
    val farmOwnerId: String,
    val docId: String,
    @PrimaryKey val id: Int? = null
) {

    fun toFarmInformation(): FarmInformation = FarmInformation(
        locationName = this.locationName,
        geoPoint = this.geoPoint,
        docId = this.docId,
        farmImageUrl = this.farmImageUrl,
        farmOwnerId = this.farmOwnerId
    )

}