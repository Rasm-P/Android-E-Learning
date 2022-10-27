package com.example.elearningapp.repositories

import com.example.elearningapp.common.LoginState
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryInterface {

    suspend fun login(email: String, password: String): Flow<LoginState>

    suspend fun register(email: String, password: String): Flow<LoginState>

    suspend fun resetPassword(email: String): Flow<LoginState>
}