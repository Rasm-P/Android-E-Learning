package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.models.CourseInformation
import kotlinx.coroutines.flow.Flow

interface CourseRepositoryInterface {

    suspend fun fetchTrendingCourses(): Flow<ActionState<List<CourseInformation>>>

    suspend fun fetchAllCourses(): Flow<ActionState<List<CourseInformation>>>

    suspend fun fetchCourseContentByName(name: String): Flow<ActionState<CourseContent?>>

}