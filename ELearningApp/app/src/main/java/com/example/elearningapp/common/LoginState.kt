package com.example.elearningapp.common

sealed class LoginState {

    object  Initial : LoginState()

    object Loading : LoginState()

    object Success : LoginState()

    data class Error(val message: String) : LoginState()
}