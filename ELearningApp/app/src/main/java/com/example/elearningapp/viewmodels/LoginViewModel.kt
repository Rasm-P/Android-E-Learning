package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.LoginState
import com.example.elearningapp.repositories.LoginRepositoryInterface
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject internal constructor(private val _loginRepository: LoginRepositoryInterface): ViewModel() {

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: State<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginRepository.login(email, password).collect {
               response -> _loginState.value = response
            }
        }
    }

    fun logout() {
        Firebase.auth.signOut()
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _loginRepository.register(email, password).collect {
                response -> _loginState.value = response
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _loginRepository.resetPassword(email).collect {
                response -> _loginState.value = response
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Initial
    }

}