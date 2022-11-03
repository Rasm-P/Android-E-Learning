package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepositoryInterface {

    suspend fun fetchTrendingCourses(): Flow<ActionState<List<Course>>>

}