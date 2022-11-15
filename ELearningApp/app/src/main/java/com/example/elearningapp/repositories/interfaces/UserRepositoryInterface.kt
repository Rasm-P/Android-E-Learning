package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    suspend fun fetchUser(): Flow<ActionState<User>>

    suspend fun addUser(user: User): Flow<ActionState<User>>

    suspend fun updateUserName(name: String): Flow<ActionState<String>>

    suspend fun updateUserStudyProgramme(programme: Programme): Flow<ActionState<String>>

    suspend fun updateUserActiveCourses(activeCourses: List<CourseStatus>): Flow<ActionState<String>>

    suspend fun deleteUser(): Flow<ActionState<String>>
}