package com.example.elearningapp.repositories

import android.util.Log
import com.example.elearningapp.common.LoginState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


//@Singleton
class LoginRepository @Inject internal constructor(private val firebaseAuth: FirebaseAuth) : LoginRepositoryInterface {

    private val errorMessage = "Oh... Something went wrong"

    override suspend fun login(email: String, password: String) = flow {
        try {
            emit(LoginState.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(LoginState.Success)
        } catch (e: Exception) {
            emit(LoginState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun register(email: String, password: String) = flow {
        try {
            emit(LoginState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(LoginState.Success)
        } catch (e: Exception) {
            emit(LoginState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun resetPassword(email: String) = flow {
        try {
            emit(LoginState.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(LoginState.Success)
        } catch (e: Exception) {
            emit(LoginState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

}