package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.interfaces.UserRepositoryInterface
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

    private val _updateState = mutableStateOf<ActionState<String>>(ActionState.Initial)
    val updateState: State<ActionState<String>> = _updateState

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
        _userData.value.name = name
        viewModelScope.launch {
            _userRepository.updateUserName(name).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun updateUserStudyProgramme(programme: Programme) {
        _userData.value.studyProgramme = programme
        viewModelScope.launch {
            _userRepository.updateUserStudyProgramme(programme).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun getUserCourseStepsCompleted(courseName: String): Int {
        val currentSteps = _userData.value.activeCourses.find { course -> course.courseInformation.courseName == courseName }
        return currentSteps?.stepsCompleted ?: 0
    }

    fun updateUserCourseSteps(courseName: String, updateStepsCompleted: Int) {
        val currentSteps = _userData.value.activeCourses.find { course -> course.courseInformation.courseName == courseName }
        if(currentSteps != null && currentSteps.stepsCompleted < updateStepsCompleted) {
            currentSteps.stepsCompleted = updateStepsCompleted
            viewModelScope.launch {
                _userRepository.updateUserActiveCourses(_userData.value.activeCourses).collect {
                        response -> _updateState.value = response
                }
            }
        }
    }

    fun getUserCourseQuizAnswers(courseName: String) : List<Int> {
        val course = _userData.value.activeCourses.find { course -> course.courseInformation.courseName == courseName }
        return course?.courseQuizAnswers ?: emptyList()
    }

    fun updateUserCourseQuizAnswers(courseName: String, courseQuizAnswers: List<Int>) {
        val course = _userData.value.activeCourses.find { course -> course.courseInformation.courseName == courseName }
        if(course != null) {
            course.courseQuizAnswers = courseQuizAnswers
            viewModelScope.launch {
                _userRepository.updateUserActiveCourses(_userData.value.activeCourses).collect {
                        response -> _updateState.value = response
                }
            }
        }
    }

    fun updateUserActiveCourses(courseInformation: CourseInformation) {
        _userData.value.activeCourses.add(CourseStatus(courseInformation = courseInformation))
        viewModelScope.launch {
            _userRepository.updateUserActiveCourses(_userData.value.activeCourses).collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun hasUserStartedCourse(courseName: String) : Boolean {
        return _userData.value.activeCourses.any { course -> course.courseInformation.courseName == courseName }
    }

    fun deleteUserData() {
        _userData.value = User()
        viewModelScope.launch {
            _userRepository.deleteUser().collect {
                    response -> _updateState.value = response
            }
        }
    }

    fun resetUserActionState() {
        _userState.value = ActionState.Initial
        _updateState.value = ActionState.Initial
    }

}