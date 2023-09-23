package com.example.haven.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel

class SplashViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        _isUserLoggedIn.value = auth.currentUser != null
    }
}