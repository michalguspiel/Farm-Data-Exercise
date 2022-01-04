package com.erdees.farmdataexercise.feature_auth.domain.repository

import com.erdees.farmdataexercise.feature_auth.domain.util.Registration
import com.erdees.farmdataexercise.model.Response
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun signUpWithEmail(registration: Registration) : Flow<Response<Boolean>>

    fun createUserDocument(registration: Registration) : Task<Void>?

    suspend fun signInWithEmail(email: String, password: String) : Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>
}