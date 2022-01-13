package com.erdees.farmdataexercise.feature_FarmData.data.local

import androidx.room.TypeConverter
import com.google.firebase.firestore.GeoPoint
import com.google.gson.Gson

class GeoPointConverter {

    @TypeConverter
    fun stringToGeoPoint(string: String):GeoPoint{
     return   Gson().fromJson(string,GeoPoint::class.java)
    }

    @TypeConverter
    fun geoPointToString(geoPoint: GeoPoint):String{
    return Gson().toJson(geoPoint)
    }

}