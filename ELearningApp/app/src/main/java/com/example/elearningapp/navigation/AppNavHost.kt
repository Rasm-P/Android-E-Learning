package com.example.elearningapp.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.elearningapp.models.User
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.ui.views.account.AccountScreen
import com.example.elearningapp.ui.views.courses.CourseOverviewScreen
import com.example.elearningapp.ui.views.login.LoginScreen
import com.example.elearningapp.ui.views.login.ProgrammeScreen
import com.example.elearningapp.ui.views.login.RegisterScreen
import com.example.elearningapp.ui.views.login.WelcomeScreen
import com.example.elearningapp.ui.views.notes.NotesOverviewScreen
import com.example.elearningapp.ui.views.overview.OverviewScreen
import com.example.elearningapp.viewmodels.*
import java.time.OffsetDateTime

// Built with inspiration from: https://developer.android.com/codelabs/jetpack-compose-navigation#0

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    programmeViewModel: ProgrammeViewModel,
    courseViewModel: CourseViewModel,
    noteViewModel: NoteViewModel
) {
    var isFirstTimeUser by remember { mutableStateOf(false) }
    val startDestination = if (loginViewModel.isLoggedIn() && !isFirstTimeUser) AppNavigationFlow.OverviewFlow.route else AppNavigationFlow.LoginFlow.route

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        navigation(route = AppNavigationFlow.LoginFlow.route, startDestination = LoginDestination.Welcome.route) {
            composable(route = LoginDestination.Welcome.route) {
                WelcomeScreen(
                    navigateLogin = {navController.navigateSingleTopTo(LoginDestination.Login.route)},
                    navigateRegister = {navController.navigateSingleTopTo(LoginDestination.Register.route)}
                )
            }
            composable(route = LoginDestination.Login.route) {
                LoginScreen(
                    navigateRegister = {navController.navigateSingleTopTo(LoginDestination.Register.route)},
                    navigateOverview = {navController.navigate(navController.graph.startDestinationId)},
                    loginState = loginViewModel.loginState.value,
                    resetActionState = {loginViewModel.resetLoginActionState()},
                    onLogin = {email, password -> loginViewModel.login(email, password)},
                    onPasswordReset = {email -> loginViewModel.resetPassword(email)},
                    restPasswordState = loginViewModel.resetState.value,
                    fetchUser = {userViewModel.fetchUser()}
                )
            }
            composable(route = LoginDestination.Register.route) {
                RegisterScreen(
                    navigateLogin = {navController.navigateSingleTopTo(LoginDestination.Login.route)},
                    navigateProgramme = {navController.navigate(LoginDestination.Programme.route) {popUpTo(0); launchSingleTop = true} },
                    loginState = loginViewModel.loginState.value,
                    resetActionState = {loginViewModel.resetLoginActionState()},
                    onRegister = {email, password -> loginViewModel.register(email, password)},
                    setFirstTimeUser = {isFirstTimeUser = true}
                )
            }
            composable(route = LoginDestination.Programme.route) {
                ProgrammeScreen(navigateOverview = {navController.navigate(navController.graph.startDestinationId)},
                    fetchProgrammes = {programmeViewModel.fetchProgrammes()},
                    programmeState = programmeViewModel.programmeState.value,
                    setFirstTimeUser = {isFirstTimeUser = false},
                    addUserData = {studentName, programme -> userViewModel.addUser(User(studentName, programme, emptyList()))},
                    userState = userViewModel.userState.value,
                    resetActionState = {userViewModel.resetUserActionState()}
                )
            }
        }
        navigation(route = AppNavigationFlow.OverviewFlow.route, startDestination = MenuNavDestination.Overview.route) {
            composable(route = MenuNavDestination.Overview.route) {
                OverviewScreen(courseState = courseViewModel.courseState.value,
                fetchTrendingCourses = {courseViewModel.fetchTrendingCourses()},
                userCoursesStatus = userViewModel.userData.value.activeCourses,
                navigateCourseOverview = {navController.navigateSingleTopTo(MenuNavDestination.CourseOverview.route)}
                )
            }
            composable(route = MenuNavDestination.CourseOverview.route) {
                CourseOverviewScreen(programmeTopics = userViewModel.userData.value.studyProgramme.topics,
                coursesState = courseViewModel.courseState.value,
                fetchAllCourses = {courseViewModel.fetchAllCourses()},
                filterCourses = {searchFilter, topicFilter -> courseViewModel.filterCourses(searchFilter, topicFilter)}
                )
            }
            composable(route = MenuNavDestination.NotesOverview.route) {
                NotesOverviewScreen(allNotesState = noteViewModel.allNotesState,
                saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                editNote = {id, title, noteText -> noteViewModel.updateNote(id, title, noteText)},
                deleteNote = {id -> noteViewModel.deleteNote(id)}
                )
            }
            composable(route = MenuNavDestination.Account.route) {
                AccountScreen()
            }
        }
        navigation(route = AppNavigationFlow.CourseFlow.route, startDestination = CourseDestination.CourseOverview.route) {
            composable(route = CourseDestination.CourseOverview.route) {
                { /*TODO*/ }
            }
            composable(route = CourseDestination.CourseArticle.route) {
                { /*TODO*/ }
            }
            composable(route = CourseDestination.CourseVideo.route) {
                { /*TODO*/ }
            }
            composable(route = CourseDestination.CourseQuiz.route) {
                { /*TODO*/ }
            }
            composable(route = CourseDestination.CourseQuizAnswers.route) {
                { /*TODO*/ }
            }
            composable(route = CourseDestination.CourseSummary.route) {
                { /*TODO*/ }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }