package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.repositories.interfaces.LoginRepositoryInterface
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject internal constructor(private val _loginRepository: LoginRepositoryInterface): ViewModel() {

    private val _loginState = mutableStateOf<ActionState<Boolean>>(ActionState.Initial)
    val loginState: State<ActionState<Boolean>> = _loginState

    private val _resetState = mutableStateOf<ActionState<Boolean>>(ActionState.Initial)
    val resetState: State<ActionState<Boolean>> = _resetState

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

    fun isLoggedIn() : Boolean {
        val user = Firebase.auth.currentUser
        return user != null
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
                response -> _resetState.value = response
            }
        }
    }

    fun getEmail(): String? {
        return Firebase.auth.currentUser!!.email
    }

    fun updateEmail(email: String) {
        viewModelScope.launch {
            _loginRepository.updateEmail(email).collect {
                    response -> _loginState.value = response
            }
        }
    }

    fun resetLoginActionState() {
        _loginState.value = ActionState.Initial
        _resetState.value = ActionState.Initial
    }

}