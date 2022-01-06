package com.erdees.farmdataexercise.feature_viewFarmData.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FarmInformationEntity::class],version = 1)
@TypeConverters(GeoPointConverter::class)
abstract class FarmInformationDatabase : RoomDatabase() {
    abstract val farmInformationDao : FarmInformationDao
}