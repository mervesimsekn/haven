package com.example.haven.ui.signup

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
class SignUpViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private var _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>
        get() = _signUpState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            when (val result = authenticationRepository.signUp(email, password)) {
                is Resource.Success -> {
                    _signUpState.value = SignUpState.Success
                }

                is Resource.Error -> {
                    _signUpState.value = SignUpState.Error(result.throwable)
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

sealed interface SignUpState {
    data object Success : SignUpState
    data class Error(val throwable: Throwable) : SignUpState
}