package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.data.CourseData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject internal constructor() : CourseRepositoryInterface {

    // Using a fake API call to simulate interaction with a real serverside API
    override suspend fun fetchTrendingCourses() = flow {
        emit(ActionState.Loading)
        //API call
        delay(500)
        val result = CourseData.trendingCourses
        emit(ActionState.Success(result))
    }

}