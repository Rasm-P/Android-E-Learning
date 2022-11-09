package com.example.elearningapp.common

// Inspired by: https://medium.com/codex/kotlin-sealed-classes-for-better-handling-of-api-response-6aa1fbd23c76

sealed class ActionState<out T> {

    object  Initial : ActionState<Nothing>()

    object Loading : ActionState<Nothing>()

    data class Success<T>(val data: T) : ActionState<T>()

    data class Error(val message: String) : ActionState<Nothing>()
}