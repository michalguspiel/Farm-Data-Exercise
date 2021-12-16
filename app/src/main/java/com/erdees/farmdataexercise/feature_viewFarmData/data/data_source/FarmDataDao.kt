package com.erdees.farmdataexercise.feature_viewFarmData.data.data_source

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmSingleDataRead
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FarmDataDao {


    fun getFarmData(): Flow<MutableList<FarmSingleDataRead>> = callbackFlow {

    }


}