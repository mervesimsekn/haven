package com.example.haven.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haven.common.Resource
import com.example.haven.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState>
        get() = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            when (val result = authenticationRepository.signIn(email, password)) {
                is Resource.Success -> {
                    _signInState.value = SignInState.Success
                }

                is Resource.Error -> {
                    _signInState.value = SignInState.Error(result.throwable)
                }
            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return authenticationRepository.isEmailValid(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return authenticationRepository.isPasswordValid(password)
    }
}

sealed interface SignInState {
    data object Success : SignInState
    data class Error(val throwable: Throwable) : SignInState
}