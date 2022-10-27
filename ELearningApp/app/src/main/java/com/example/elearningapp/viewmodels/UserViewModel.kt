package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.StudyProgramme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.UserRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject internal constructor(private val _userRepository: UserRepositoryInterface): ViewModel() {

    private val _userState = mutableStateOf<ActionState<User>>(ActionState.Initial)
    val userState: State<ActionState<User>> = _userState

    private val _updateState = mutableStateOf<ActionState<Boolean>>(ActionState.Initial)
    val updateState: State<ActionState<Boolean>> = _updateState

    fun fetchUser() {
        viewModelScope.launch {
            _userRepository.fetchUser().collect {
                    response -> _userState.value = response
            }
        }
    }

    fun addUser(user: User) {
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

    fun updateUserStudyProgramme(studyProgramme: StudyProgramme) {
        viewModelScope.launch {
            _userRepository.updateUserStudyProgramme(studyProgramme).collect {
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

    fun resetUserState() {
        _userState.value = ActionState.Initial
        _updateState.value = ActionState.Initial
    }

}