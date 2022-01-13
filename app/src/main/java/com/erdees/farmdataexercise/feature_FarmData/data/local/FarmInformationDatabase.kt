package com.erdees.farmdataexercise.feature_FarmData.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**This stores data about farms locally, therefore helps to limit unnecessary data reads.
 * Data is downloaded once from the server when the app starts and saved in this database
 * from where is fetched throughout the lifecycle of application*/
@Database(entities = [FarmInformationEntity::class],version = 1)
@TypeConverters(GeoPointConverter::class)
abstract class FarmInformationDatabase : RoomDatabase() {
    abstract val farmInformationDao : FarmInformationDao
}