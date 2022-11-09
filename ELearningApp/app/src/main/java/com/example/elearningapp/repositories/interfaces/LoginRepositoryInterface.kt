package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.common.ActionState
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryInterface {

    suspend fun login(email: String, password: String): Flow<ActionState<Boolean>>

    suspend fun register(email: String, password: String): Flow<ActionState<Boolean>>

    suspend fun resetPassword(email: String): Flow<ActionState<Boolean>>

    suspend fun updateEmail(email: String): Flow<ActionState<Boolean>>

}