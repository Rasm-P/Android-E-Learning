package com.example.elearningapp.repositories

import android.util.Log
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.repositories.interfaces.LoginRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginRepository @Inject internal constructor(private val firebaseAuth: FirebaseAuth) :
    LoginRepositoryInterface {

    private val errorMessage = "Something went wrong!"

    override suspend fun login(email: String, password: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(ActionState.Success("Login successful!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun register(email: String, password: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email.trim(), password.trim()).await()
            emit(ActionState.Success("Register successful!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun resetPassword(email: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(ActionState.Success("Email password reset request has been sent!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override suspend fun updateEmail(email: String) = flow {
        try {
            emit(ActionState.Loading)
            firebaseAuth.currentUser!!.updateEmail(email).await()
            emit(ActionState.Success("Account email has been updated!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: Login", e.message ?: errorMessage)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun isLoggedIn(): Boolean {
        val user = currentUser()
        return user != null
    }

}