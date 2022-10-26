package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryInterface {

    suspend fun login(email: String, password: String): Flow<ActionState>

    suspend fun register(email: String, password: String): Flow<ActionState>

    suspend fun resetPassword(email: String): Flow<ActionState>
}