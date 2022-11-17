package com.example.elearningapp.viewmodels

import com.example.elearningapp.MainCoroutineRule
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.FakeDataSource
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.FakeLoginRepository
import com.example.elearningapp.repositories.FakeUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule


@OptIn(ExperimentalCoroutinesApi::class)
internal class UserViewModelTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeLoginRepository: FakeLoginRepository
    private lateinit var userViewModel: UserViewModel

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeDataSource = FakeDataSource()
        fakeUserRepository = FakeUserRepository(fakeDataSource)
        fakeLoginRepository = FakeLoginRepository()
    }

    @Test
    fun test_fetchUser_success() = runTest {
        val user = User("1")
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Assert that userState is Initial
        //assertEquals(ActionState.Initial, userViewModel.userState)

        //Fetch user is called
        userViewModel.fetchUser()

        //Assert that userState is Loading
        fakeDataSource.emitUser(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.userState.value)

        //Assert that userState is Success
        fakeDataSource.emitUser(ActionState.Success(user))
        assertEquals(ActionState.Success(user), userViewModel.userState.value)

        //Assert result
        val result = userViewModel.userState.value as ActionState.Success
        assertEquals(user, result.data)
    }

    @Test
    fun addUser() {
    }

    @Test
    fun updateUserName() {
    }

    @Test
    fun updateUserStudyProgramme() {
    }

    @Test
    fun getUserCourseStepsCompleted() {
    }

    @Test
    fun updateUserCourseSteps() {
    }

    @Test
    fun getUserCourseQuizAnswers() {
    }

    @Test
    fun updateUserCourseQuizAnswers() {
    }

    @Test
    fun updateUserActiveCourses() {
    }

    @Test
    fun hasUserStartedCourse() {
    }

    @Test
    fun deleteUserData() {
    }
}