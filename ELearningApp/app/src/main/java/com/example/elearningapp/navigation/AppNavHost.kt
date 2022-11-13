package com.example.elearningapp.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.elearningapp.models.User
import com.example.elearningapp.ui.views.account.AccountScreen
import com.example.elearningapp.ui.views.courses.*
import com.example.elearningapp.ui.views.login.LoginScreen
import com.example.elearningapp.ui.views.login.ProgrammeScreen
import com.example.elearningapp.ui.views.login.RegisterScreen
import com.example.elearningapp.ui.views.login.WelcomeScreen
import com.example.elearningapp.ui.views.notes.NotesOverviewScreen
import com.example.elearningapp.ui.views.overview.OverviewScreen
import com.example.elearningapp.viewmodels.*

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
                    addUserData = {studentName, programme -> userViewModel.addUser(User(studentName, programme, ArrayList()))},
                    userState = userViewModel.userState.value,
                    resetActionState = {userViewModel.resetUserActionState()}
                )
            }
        }
        navigation(route = AppNavigationFlow.OverviewFlow.route, startDestination = MenuNavDestination.Overview.route) {
            composable(route = MenuNavDestination.Overview.route) {
                OverviewScreen(courseInformationState = courseViewModel.courseInformationState.value,
                fetchTrendingCourses = {courseViewModel.fetchTrendingCourses()},
                userCoursesStatus = userViewModel.userData.value.activeCourses,
                onViewCourse = { courseInformation -> courseViewModel.setCourseInformation(courseInformation)
                    navController.navigateSingleTopTo(AppNavigationFlow.CourseFlow.route)}
                )
            }
            composable(route = MenuNavDestination.CourseOverview.route) {
                CourseOverviewScreen(programmeTopics = userViewModel.userData.value.studyProgramme.topics,
                coursesState = courseViewModel.courseInformationState.value,
                fetchAllCourses = {courseViewModel.fetchAllCourses()},
                filterCourses = {searchFilter, topicFilter -> courseViewModel.filterCourses(searchFilter, topicFilter)},
                onViewCourse = { courseInformation -> courseViewModel.setCourseInformation(courseInformation)
                    navController.navigateSingleTopTo(AppNavigationFlow.CourseFlow.route)}
                )
            }
            composable(route = MenuNavDestination.NotesOverview.route) {
                NotesOverviewScreen(allNotesState = noteViewModel.allNotesState,
                saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                editNote = {id, title, noteText -> noteViewModel.updateNote(id, title, noteText)},
                deleteNote = {id -> noteViewModel.deleteNote(id)},
                filterNotes = {notes, searchFilter -> noteViewModel.filterNotes(notes, searchFilter)}
                )
            }
            composable(route = MenuNavDestination.Account.route) {
                AccountScreen(userData = userViewModel.userData.value,
                userEmail = loginViewModel.getEmail())
            }
        }
        navigation(route = AppNavigationFlow.CourseFlow.route, startDestination = CourseDestination.CourseDetails.route) {
            composable(route = CourseDestination.CourseDetails.route) {
                CourseDetailsScreen(courseInformation = courseViewModel.courseInformation.value,
                fetchCourseContent = {courseViewModel.fetchCourseContentByName()},
                courseContentState = courseViewModel.courseContentState.value,
                stepStatus = courseViewModel.getStepStatus(userViewModel.userData.value.activeCourses),
                hasUserStartedCourse = {courseName -> userViewModel.hasUserStartedCourse(courseName)},
                updateUserActiveCourses = {courseInformation -> userViewModel.updateUserActiveCourses(courseInformation)
                    navController.navigateSingleTopTo(CourseDestination.CourseArticle.route)},
                navigateToScreenStep = {step -> navController.navigateSingleTopTo(courseNavScreens[if (step > 0) step else 1].route)}
                )
            }
            composable(route = CourseDestination.CourseArticle.route) {
                CourseArticleScreen(courseContentState = courseViewModel.courseContentState.value,
                saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)}
                )
            }
            composable(route = CourseDestination.CourseVideo.route) {
                CourseVideoArticleScreen()
            }
            composable(route = CourseDestination.CourseQuiz.route) {
                CourseQuizTestScreen()
            }
            composable(route = CourseDestination.CourseQuizAnswers.route) {
                CourseQuizResultsScreen()
            }
            composable(route = CourseDestination.CourseSummary.route) {
                CourseSummaryScreen()
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