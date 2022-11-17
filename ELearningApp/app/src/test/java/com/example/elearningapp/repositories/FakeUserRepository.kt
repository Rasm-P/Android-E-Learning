package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.FakeDataSource
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.interfaces.UserRepositoryInterface
import kotlinx.coroutines.flow.Flow

class FakeUserRepository(private val dataSource: FakeDataSource): UserRepositoryInterface {

    override suspend fun fetchUser(): Flow<ActionState<User>> {
        return dataSource.userFlowActionState()
    }

    override suspend fun addUser(user: User): Flow<ActionState<User>> {
        return dataSource.userFlowActionState()
    }

    override suspend fun updateUserName(name: String): Flow<ActionState<String>> {
        return dataSource.stringFlowActionState()
    }

    override suspend fun updateUserStudyProgramme(programme: Programme): Flow<ActionState<String>> {
        return dataSource.stringFlowActionState()
    }

    override suspend fun updateUserActiveCourses(activeCourses: List<CourseStatus>): Flow<ActionState<String>> {
        return dataSource.stringFlowActionState()
    }

    override suspend fun deleteUser(): Flow<ActionState<String>> {
        return dataSource.stringFlowActionState()
    }

}