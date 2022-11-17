package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.common.ActionState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryInterface {

    suspend fun login(email: String, password: String): Flow<ActionState<String>>

    suspend fun register(email: String, password: String): Flow<ActionState<String>>

    suspend fun resetPassword(email: String): Flow<ActionState<String>>

    suspend fun updateEmail(email: String): Flow<ActionState<String>>

    fun logout()

    fun currentUser(): FirebaseUser?

    fun isLoggedIn(): Boolean

}