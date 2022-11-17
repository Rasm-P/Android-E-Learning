package com.example.elearningapp.viewmodels

import com.example.elearningapp.MainCoroutineRule
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.FakeDataSource
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
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
        //Arrange
        val user = User("Test")
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: fetchUser is called
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
    fun test_fetchUser_error() = runTest {
        //Arrange
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: fetchUser is called
        userViewModel.fetchUser()

        //Assert that userState is Loading
        fakeDataSource.emitUser(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.userState.value)
        //Assert that userState is Error
        fakeDataSource.emitUser(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.userState.value)
        //Assert result
        val result = userViewModel.userState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_addUser_success() = runTest {
        //Arrange
        val user = User("Test")
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: addUser is called
        userViewModel.addUser(user)

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
    fun test_addUser_error() = runTest {
        //Arrange
        val user = User("Test")
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: addUser is called
        userViewModel.addUser(user)

        //Assert that userState is Loading
        fakeDataSource.emitUser(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.userState.value)
        //Assert that userState is Error
        fakeDataSource.emitUser(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.userState.value)
        //Assert result
        val result = userViewModel.userState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_updateUserName_success() = runTest {
        //Arrange
        val name = "Test"
        val message = "Username has been updated!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: updateUserName is called
        userViewModel.updateUserName(name)

        //Assert that userState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that userState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_updateUserName_error() = runTest {
        //Arrange
        val name = "Test"
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: updateUserName is called
        userViewModel.updateUserName(name)

        //Assert that userState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that userState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_updateUserStudyProgramme_success() = runTest {
        //Arrange
        val programme = Programme()
        val message = "Study programme has been updated!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: updateUserStudyProgramme is called
        userViewModel.updateUserStudyProgramme(programme)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_updateUserStudyProgramme_error() = runTest {
        //Arrange
        val programme = Programme()
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)

        //Act: updateUserStudyProgramme is called
        userViewModel.updateUserStudyProgramme(programme)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_getUserCourseStepsCompleted() = runTest {
        //Arrange
        val courseName = "course1"
        val stepsCompleted = 3
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName),stepsCompleted)))
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: getUserCourseStepsCompleted is called
        val result = userViewModel.getUserCourseStepsCompleted(courseName)

        //Assert result
        assertEquals(stepsCompleted, result)
    }

    @Test
    fun test_updateUserCourseSteps_success() = runTest {
        //Arrange
        val courseName = "course1"
        val stepsCompleted = 3
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName),stepsCompleted)))
        val updateStepsCompleted = 4
        val message = "Active courses has been updated!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserCourseSteps is called
        userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_updateUserCourseSteps_error() = runTest {
        //Arrange
        val courseName = "course1"
        val stepsCompleted = 3
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName),stepsCompleted)))
        val updateStepsCompleted = 4
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserCourseSteps is called
        userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_getUserCourseQuizAnswers() = runTest {
        //Arrange
        val courseName = "course1"
        val quizAnswers = listOf(1,3,2,1,4)
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName), courseQuizAnswers = quizAnswers)))
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: getUserCourseQuizAnswers is called
        val result = userViewModel.getUserCourseQuizAnswers(courseName)

        //Assert result
        assertEquals(quizAnswers, result)
    }

    @Test
    fun test_updateUserCourseQuizAnswers_success() = runTest {
        //Arrange
        val courseName = "course1"
        val quizAnswers = listOf(1,3,2,1,4)
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName), courseQuizAnswers = quizAnswers)))
        val newQuizAnswers = listOf(1,1,1,1,1)
        val message = "Active courses has been updated!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserCourseQuizAnswers is called
        userViewModel.updateUserCourseQuizAnswers(courseName, newQuizAnswers)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_updateUserCourseQuizAnswers_error() = runTest {
        //Arrange
        val courseName = "course1"
        val quizAnswers = listOf(1,3,2,1,4)
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName), courseQuizAnswers = quizAnswers)))
        val newQuizAnswers = listOf(1,1,1,1,1)
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserCourseQuizAnswers is called
        userViewModel.updateUserCourseQuizAnswers(courseName, newQuizAnswers)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_updateUserActiveCourses_success() = runTest {
        //Arrange
        val courseName = "course1"
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName))))
        val newActiveCourse = CourseInformation("course2")
        val message = "Active courses has been updated!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserActiveCourses is called
        userViewModel.updateUserActiveCourses(newActiveCourse)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_updateUserActiveCourses_error() = runTest {
        //Arrange
        val courseName = "course1"
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName))))
        val newActiveCourse = CourseInformation("course2")
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: updateUserActiveCourses is called
        userViewModel.updateUserActiveCourses(newActiveCourse)

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_hasUserStartedCourse_yes() = runTest {
        //Arrange
        val courseName = "course1"
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName))))
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: hasUserStartedCourse is called
        val result = userViewModel.hasUserStartedCourse(courseName)

        //Assert result
        assertEquals(result, true)
    }

    @Test
    fun test_hasUserStartedCourse_no() = runTest {
        //Arrange
        val courseName = "course1"
        val user = User("test", activeCourses = arrayListOf(CourseStatus(CourseInformation(courseName))))
        val anotherCourseName = "course2"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: hasUserStartedCourse is called
        val result = userViewModel.hasUserStartedCourse(anotherCourseName)

        //Assert result
        assertEquals(result, false)
    }

    @Test
    fun test_deleteUserData_success() = runTest {
        //Arrange
        val user = User("test")
        val message = "User was successfully deleted!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: deleteUserData is called
        userViewModel.deleteUserData()

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Success
        fakeDataSource.emitString(ActionState.Success(message))
        assertEquals(ActionState.Success(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Success
        assertEquals(message, result.data)
    }

    @Test
    fun test_deleteUserData_error() = runTest {
        //Arrange
        val user = User("test")
        val message = "Something went wrong!"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))

        //Act: deleteUserData is called
        userViewModel.deleteUserData()

        //Assert that updateState is Loading
        fakeDataSource.emitString(ActionState.Loading)
        assertEquals(ActionState.Loading, userViewModel.updateState.value)
        //Assert that updateState is Error
        fakeDataSource.emitString(ActionState.Error(message))
        assertEquals(ActionState.Error(message), userViewModel.updateState.value)
        //Assert result
        val result = userViewModel.updateState.value as ActionState.Error
        assertEquals(message, result.message)
    }

    @Test
    fun test_resetUserActionState() = runTest {
        //Arrange
        val user = User("test")
        val message = "message"
        userViewModel = UserViewModel(fakeUserRepository, fakeLoginRepository)
        fakeDataSource.emitUser(ActionState.Success(user))
        fakeDataSource.emitString(ActionState.Success(message))

        //Act: resetUserActionState is called
        userViewModel.resetUserActionState()

        //Assert that userState is Initial
        assertEquals(ActionState.Initial, userViewModel.userState.value)
        //Assert that updateState is Initial
        assertEquals(ActionState.Initial, userViewModel.updateState.value)
    }

}