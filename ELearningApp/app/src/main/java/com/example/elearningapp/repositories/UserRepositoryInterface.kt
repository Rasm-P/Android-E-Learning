package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.StudyProgramme
import com.example.elearningapp.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    suspend fun fetchUser(): Flow<ActionState<User>>

    suspend fun addUser(user: User): Flow<ActionState<User>>

    suspend fun updateUserName(name: String): Flow<ActionState<Boolean>>

    suspend fun updateUserStudyProgramme(studyProgramme: StudyProgramme): Flow<ActionState<Boolean>>

    suspend fun updateUserActiveCourses(activeCourses: List<CourseStatus>): Flow<ActionState<Boolean>>
}