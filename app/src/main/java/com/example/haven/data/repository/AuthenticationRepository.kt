package com.example.haven.data.repository

import android.util.Patterns
import com.example.haven.common.Resource
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String): Resource<Boolean> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).isSuccessful
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun signUp(email: String, password: String): Resource<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).isSuccessful
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }
}