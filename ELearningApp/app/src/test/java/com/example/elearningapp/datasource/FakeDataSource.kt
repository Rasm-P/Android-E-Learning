package com.example.elearningapp.datasource

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// Built with inspiration from: https://developer.android.com/kotlin/flow/test

class FakeDataSource {
    private val userFlow = MutableSharedFlow<ActionState<User>>()
    suspend fun emitUser(value: ActionState<User>) = userFlow.emit(value)
    fun userFlowActionState(): Flow<ActionState<User>> = userFlow

    private val stringFlow = MutableSharedFlow<ActionState<String>>()
    suspend fun emitString(value: ActionState<String>) = stringFlow.emit(value)
    fun stringFlowActionState(): Flow<ActionState<String>> = stringFlow
}