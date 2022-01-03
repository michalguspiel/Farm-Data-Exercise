package com.erdees.farmdataexercise.di

import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.FarmDataRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.TemporaryFarmDataRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.*
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
    fun provideFarmDataReference(db: FirebaseFirestore) = db.collection("farm_data")

    @Provides
    fun provideFarmDataRepository(
        farmDataReference: CollectionReference,
    ): FarmDataRepository = FarmDataRepositoryImpl(farmDataReference)

    @Provides
    fun provideTemporaryFarmDataRepository(): TemporaryFarmDataRepository =
        TemporaryFarmDataRepositoryImpl.getInstance()


    @Provides
    fun provideFarmDataUseCases(
        repository: FarmDataRepository,
        temporaryFarmDataRepository: TemporaryFarmDataRepository
    ) = UseCases(
        getFarmData = GetFarmData(repository),
        addFarmData = AddFarmData(repository),
        getTemporaryFarmData = GetTemporaryFarmData(temporaryFarmDataRepository),
        saveTemporaryFarmData = SaveTemporaryFarmData(temporaryFarmDataRepository)
    )

}