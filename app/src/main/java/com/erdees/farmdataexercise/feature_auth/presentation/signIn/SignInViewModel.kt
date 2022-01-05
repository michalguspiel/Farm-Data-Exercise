package com.erdees.farmdataexercise.feature_auth.presentation.signIn

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    val isUserAuthenticated get() = useCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState

    fun signInWithEmail(email: String, password: String){
        viewModelScope.launch{
            useCases.signInWithEmail(email,password).collect{ response ->
                _signInState.value = response
            }
        }
    }

}