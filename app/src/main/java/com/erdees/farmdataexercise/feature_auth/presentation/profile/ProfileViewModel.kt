package com.erdees.farmdataexercise.feature_auth.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_auth.domain.model.FarmDataUser
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@InternalCoroutinesApi
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    val isUserAuthenticated get() = useCases.isUserAuthenticated()

    private val _currentUserDocument = mutableStateOf<Response<FarmDataUser>>(Response.Loading)
    val currentUserDocument : State<Response<FarmDataUser>> = _currentUserDocument

    private val _isUserSignedOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val isUserSignedOutState: State<Response<Boolean>> = _isUserSignedOutState

    fun getCurrentUserDocument() {
        viewModelScope.launch {
            useCases.getCurrentUserDocument.invoke().collect { response ->
                _currentUserDocument.value = response
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            useCases.signOut().collect { response ->
                _isUserSignedOutState.value = response
            }
        }
    }

}