package com.erdees.farmdataexercise.feature_viewFarmData.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FarmInformationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmInformation(information: List<FarmInformationEntity>)

    @Query("DELETE FROM FarmInformationEntity")
    suspend fun deleteFarmInformation()

    @Query("SELECT * FROM FarmInformationEntity")
    suspend fun getFarmInformation(): List<FarmInformationEntity>

}