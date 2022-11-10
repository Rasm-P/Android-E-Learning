package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.repositories.interfaces.CourseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject internal constructor(private val _courseRepository: CourseRepositoryInterface): ViewModel() {

    private val _courseInformationState = mutableStateOf<ActionState<List<CourseInformation>>>(ActionState.Initial)
    val courseInformationState: State<ActionState<List<CourseInformation>>> = _courseInformationState

    fun fetchTrendingCourses() {
        viewModelScope.launch {
            _courseRepository.fetchTrendingCourses().collect {
                    response -> _courseInformationState.value = response
            }
        }
    }

    fun fetchAllCourses() {
        viewModelScope.launch {
            _courseRepository.fetchAllCourses().collect {
                    response -> _courseInformationState.value = response
            }
        }
    }

    fun filterCourses(searchFilter: String, topicFilter: String): List<CourseInformation> {
        return if (_courseInformationState.value is ActionState.Success) {
            var filteredCourses = (_courseInformationState.value as ActionState.Success<List<CourseInformation>>).data
            if (topicFilter != "") {
                filteredCourses = filteredCourses.filter { it.topic.lowercase() == topicFilter.lowercase() }
            }
            if (searchFilter != "") {
                filteredCourses = filteredCourses.filter { it.courseName.lowercase().contains(searchFilter.lowercase()) }
            }
            filteredCourses
        } else {
            emptyList()
        }
    }

    fun fetchCourseByName() {
        viewModelScope.launch {
            _courseRepository.fetchTrendingCourses().collect {
                    response -> _courseInformationState.value = response
            }
        }
    }

    fun resetCourseActionState() {
        _courseInformationState.value = ActionState.Initial
    }

}