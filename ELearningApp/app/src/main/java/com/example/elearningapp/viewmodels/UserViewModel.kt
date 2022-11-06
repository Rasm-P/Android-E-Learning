package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.UserRepositoryInterface
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject internal constructor(private val _userRepository: UserRepositoryInterface): ViewModel() {

    private val _userData = mutableStateOf(User())
    val userData: State<User> = _userData

    private val _userState = mutableStateOf<ActionState<User>>(ActionState.Initial)
    val userState: State<ActionState<User>> = _userState

    private val _updateState = mutableStateOf<ActionState<Boolean>>(ActionState.Initial)
    val updateState: State<ActionState<Boolean>> = _updateState

    init {
        if (Firebase.auth.currentUser != null) {
            fetchUser()
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            _userRepository.fetchUser().collect {
                    response -> _userState.value = response
                    if (response is ActionState.Success) _userData.value = response.data
            }
        }
    }

    fun addUser(user: User) {
        _userData.value = user
        viewModelScope.launch {
            _userRepository.addUser(user).collect {
                    response -> _userState.value = response
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            _userRepository.updateUserName(name).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun updateUserStudyProgramme(programme: Programme) {
        viewModelScope.launch {
            _userRepository.updateUserStudyProgramme(programme).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun updateUserActiveCourses(activeCourses: List<CourseStatus>) {
        viewModelScope.launch {
            _userRepository.updateUserActiveCourses(activeCourses).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun resetUserActionState() {
        _userState.value = ActionState.Initial
        _updateState.value = ActionState.Initial
    }

}