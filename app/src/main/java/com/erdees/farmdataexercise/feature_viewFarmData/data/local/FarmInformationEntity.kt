package com.erdees.farmdataexercise.feature_viewFarmData.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.google.firebase.firestore.GeoPoint

@Entity
class FarmInformationEntity(
     val locationName: String,
    @TypeConverters(GeoPointConverter::class) val  geoPoint: GeoPoint,
     val farmImageUrl : String,
     val docId : String,
    @PrimaryKey val id: Int? = null
) {

    fun toFarmInformation() : FarmInformation = FarmInformation(this.locationName,this.geoPoint,this.docId,this.farmImageUrl)

}