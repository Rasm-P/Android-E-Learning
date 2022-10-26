package com.example.elearningapp.repositories

import android.util.Log
import com.example.elearningapp.common.ActionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


//@Singleton
class LoginRepository @Inject internal constructor(private val firebaseAuth: FirebaseAuth) : LoginRepositoryInterface {

    private val errorMessage = "Oh... Something went wrong"

    override suspend fun login(email: String, password: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password)
            emit(ActionState.Success)
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun register(email: String, password: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password)
            emit(ActionState.Success)
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun resetPassword(email: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.sendPasswordResetEmail(email)
            emit(ActionState.Success)
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

}