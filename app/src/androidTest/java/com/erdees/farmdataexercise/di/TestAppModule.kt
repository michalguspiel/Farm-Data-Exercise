package com.erdees.farmdataexercise.di

import android.app.Application
import androidx.room.Room
import com.erdees.farmdataexercise.fakeRepositories.FakeAuthRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeFarmDataRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeFarmInformationRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeTemporaryFarmDataRepository
import com.erdees.farmdataexercise.feature_FarmData.data.local.FarmInformationDatabase
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.IsUserAuthenticated
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {


    @Provides
    fun provideFakeAuthRepository(): FakeAuthRepository =
        FakeAuthRepository()


    @Provides
    fun provideFakeFarmDataRepository(): FakeFarmDataRepository =
        FakeFarmDataRepository()

    @Provides
    fun provideFakeFarmInfoRepository(
        db: FarmInformationDatabase,
    ): FakeFarmInformationRepository = FakeFarmInformationRepository(
        dao = db.farmInformationDao,
    )

    @Provides
    fun provideFarmInformationDatabase(app: Application): FarmInformationDatabase {
        return Room.inMemoryDatabaseBuilder(app, FarmInformationDatabase::class.java).build()
    }


    @Provides
    fun provideFakeTemporaryFarmDataRepository() :FakeTemporaryFarmDataRepository =
        FakeTemporaryFarmDataRepository()

    @Provides
    fun provideFakeUseCases(fakeAuthRepository: FakeAuthRepository) = UseCases(
            isUserAuthenticated = IsUserAuthenticated(fakeAuthRepository),
            signOut = SignOut(fakeAuthRepository),
            getCurrentUserDocument = GetCurrentUserDocument(fakeAuthRepository),
            signInWithEmail = SignInWithEmail(fakeAuthRepository),
            signUpWithEmail = SignUpWithEmail(fakeAuthRepository)
        )

    @Provides
    fun provideFakeFarmDataUseCases(fakeAuthRepository: FakeAuthRepository,
                                    fakeFarmInformationRepository: FakeFarmInformationRepository,
                                    fakeFarmDataRepository: FakeFarmDataRepository,
                                    fakeTemporaryFarmDataRepository: FakeTemporaryFarmDataRepository
    ) = com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases(
    getFarmData = GetFarmData(fakeFarmDataRepository),
    addFarmData = AddFarmData(fakeFarmDataRepository),
    getTemporaryFarmData = GetTemporaryFarmData(fakeTemporaryFarmDataRepository),
    saveTemporaryFarmData = SaveTemporaryFarmData(fakeTemporaryFarmDataRepository),
    downloadAndSaveFarmsInformation = DownloadAndSaveFarmsInformation(fakeFarmInformationRepository),
    getLocalFarmsInformation = GetLocalFarmsInformation(fakeFarmInformationRepository),
    isUserAuthenticated = com.erdees.farmdataexercise.feature_FarmData.domain.use_case.IsUserAuthenticated(fakeAuthRepository),
    addFarm = AddFarm(fakeFarmInformationRepository),
    getCurrentUserId = GetCurrentUserId(fakeAuthRepository)
    )



}



