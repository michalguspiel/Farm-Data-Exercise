package com.erdees.farmdataexercise.feature_viewFarmData.data.repository

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/**NOTE :
 *
 * CREATED THIS SO WHEN FETCHING FARM DATA ONCE FROM FIREBASE I CAN SAVE IT
 * TEMPORARILY HERE IN ORDER TO PRESENT IT IN MORE DETAILED GRAPH WITHOUT THE NEED OF DOWNLOADING DATA AGAIN
 *
 * DON'T SEE A POINT OF SAVING IT IN ROOM.
 * */
class TemporaryFarmDataRepositoryImpl : TemporaryFarmDataRepository {

    private var _temporaryFarmData: MutableStateFlow<List<FarmData>> = MutableStateFlow(emptyList())
    private val temporaryFarmData = _temporaryFarmData.asStateFlow()


    override fun getFarmData(): StateFlow<List<FarmData>> {
        return temporaryFarmData
    }

    override fun saveFarmData(farmDataList: List<FarmData>) {
        _temporaryFarmData.value = farmDataList
    }

    /**Singleton*/
    companion object {
        @Volatile
        private var instance: TemporaryFarmDataRepositoryImpl? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance
                ?: TemporaryFarmDataRepositoryImpl().also { instance = it }
        }
    }

}