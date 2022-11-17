package com.example.elearningapp.datasource

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// Built with inspiration from: https://developer.android.com/kotlin/flow/test

class FakeDataSource {
    private val userflow = MutableSharedFlow<ActionState<User>>()
    suspend fun emitUser(value: ActionState<User>) = userflow.emit(value)
    fun userFlowActionState(): Flow<ActionState<User>> = userflow

    private val stringflow = MutableSharedFlow<ActionState<String>>()
    suspend fun stringUser(value: ActionState<String>) = stringflow.emit(value)
    fun stringFlowActionState(): Flow<ActionState<String>> = stringflow
}