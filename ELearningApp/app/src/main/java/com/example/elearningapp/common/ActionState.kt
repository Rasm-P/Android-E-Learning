package com.example.elearningapp.common

sealed class ActionState {

    object Loading : ActionState()

    object Success : ActionState()

    data class Error(val message: String) : ActionState()
}