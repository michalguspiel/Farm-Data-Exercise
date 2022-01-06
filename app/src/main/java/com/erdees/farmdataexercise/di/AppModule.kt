package com.erdees.farmdataexercise.di

import android.app.Application
import androidx.room.Room
import com.erdees.farmdataexercise.feature_auth.data.AuthRepositoryImpl
import com.erdees.farmdataexercise.feature_auth.domain.repository.AuthRepository
import com.erdees.farmdataexercise.feature_auth.domain.use_case.*
import com.erdees.farmdataexercise.feature_viewFarmData.data.local.FarmInformationDatabase
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.FarmDataRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.FarmInfoRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.data.repository.TemporaryFarmDataRepositoryImpl
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmInfoRepository
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.TemporaryFarmDataRepository
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.*
import com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case.UseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton


@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Named("farmData")
    fun provideFarmDataReference(db: FirebaseFirestore) = db.collection("farm_data")

    @Provides
    @Named("userReference")
    fun provideUsersReference(db: FirebaseFirestore) = db.collection("users")

    @Provides
    fun provideFarmDataRepository(
        @Named("farmData") farmDataReference: CollectionReference,
    ): FarmDataRepository = FarmDataRepositoryImpl(farmDataReference)

    @Provides
    fun provideTemporaryFarmDataRepository(): TemporaryFarmDataRepository =
        TemporaryFarmDataRepositoryImpl.getInstance()


    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        @Named("userReference") usersReference: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(auth, usersReference)

    @Provides
    fun provideAuthUseCases(repository: AuthRepository) =
        com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases(
            isUserAuthenticated = IsUserAuthenticated(repository),
            signInWithEmail = SignInWithEmail(repository),
            signUpWithEmail = SignUpWithEmail(repository),
            signOut = SignOut(repository),
            getCurrentUserDocument = GetCurrentUserDocument(repository)
        )


    @Provides
    fun provideFarmDataUseCases(
        repository: FarmDataRepository,
        temporaryFarmDataRepository: TemporaryFarmDataRepository,
        farmInfoRepository : FarmInfoRepository
    ) = UseCases(
        getFarmData = GetFarmData(repository),
        addFarmData = AddFarmData(repository),
        getTemporaryFarmData = GetTemporaryFarmData(temporaryFarmDataRepository),
        saveTemporaryFarmData = SaveTemporaryFarmData(temporaryFarmDataRepository),
        downloadAndSaveFarmsInformation = DownloadAndSaveFarmsInformation(farmInfoRepository),
        getLocalFarmsInformation = GetLocalFarmsInformation(farmInfoRepository)
    )

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Singleton
    @Provides
    fun provideFarmInformationDatabase(app: Application): FarmInformationDatabase {
        return Room.databaseBuilder(app, FarmInformationDatabase::class.java, "farm_db").build()
    }

    @Singleton
    @Provides
    fun provideFarmInfoRepository(
        db: FarmInformationDatabase,
        @Named("farmData") farmDataReference: CollectionReference
    ): FarmInfoRepository = FarmInfoRepositoryImpl(
        dao = db.farmInformationDao,
        farmDataReference = farmDataReference
    )


}
