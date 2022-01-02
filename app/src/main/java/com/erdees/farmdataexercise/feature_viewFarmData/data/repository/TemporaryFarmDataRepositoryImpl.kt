package com.erdees.farmdataexercise.feature_viewFarmData.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Singleton


/**NOTE :
 *
 * CREATED THIS SO WHEN FETCHING FARM DATA ONCE FROM FIREBASE I CAN SAVE IT
 * TEMPORARILY HERE IN ORDER TO PRESENT IT IN MORE DETAILED GRAPH WITHOUT THE NEED OF DOWNLOADING DATA AGAIN
 *
 * DON'T SEE A POINT OF SAVING IT IN ROOM THO.
 * */
class TemporaryFarmDataRepositoryImpl : TemporaryFarmDataRepository {

    private var _temporaryFarmData: MutableStateFlow<List<FarmData>> = MutableStateFlow(emptyList())
    private val temporaryFarmData = _temporaryFarmData.asStateFlow()

    init {
        Log.i("TempFarmRepo", "INITIALIZED !!!")
    }

    override fun getFarmData(): StateFlow<List<FarmData>> {
        Log.i("TempFarmRepo", " data pulled! !!!")
        temporaryFarmData.value.forEach { Log.i("TempFarmRepo", "$it.") }
        return temporaryFarmData
    }

    override fun saveFarmData(farmDataList: List<FarmData>) {
        Log.i("TempFarmRepo", "data saved! !!!")
        farmDataList.forEach { Log.i("TEMPFARMREPO", "$it") }
        _temporaryFarmData.value = farmDataList
    }

    /**Singleton*/
    companion object {
        @Volatile
        private var instance: TemporaryFarmDataRepositoryImpl? = null

        fun getInstance() = instance ?:  synchronized(this) {
            instance
                ?: TemporaryFarmDataRepositoryImpl().also {  instance = it}
        }
    }

}