package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.Course
import com.example.elearningapp.repositories.CourseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject internal constructor(private val _courseRepository: CourseRepositoryInterface): ViewModel() {

    private val _courseState = mutableStateOf<ActionState<List<Course>>>(ActionState.Initial)
    val courseState: State<ActionState<List<Course>>> = _courseState

    fun fetchProgrammes() {
        viewModelScope.launch {
            _courseRepository.fetchTrendingCourses().collect {
                    response -> _courseState.value = response
            }
        }
    }

    fun resetCourseActionState() {
        _courseState.value = ActionState.Initial
    }

}