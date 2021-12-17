package com.erdees.farmdataexercise.di

import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.FarmDataRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.FarmDataRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideFarmDataReference(db : FirebaseFirestore) = db.collection(Constants.FARM_DATA)

    @Provides
    fun provideFarmDataRepository(
        farmDataReference: CollectionReference,
    ): FarmDataRepository = FarmDataRepositoryImpl(farmDataReference)


}