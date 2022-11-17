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
    //MutableState for when user first log in
    var isFirstTimeUser by remember { mutableStateOf(false) }

    //NavHost startDestination determined by if the user is logged in and if they are a first time user
    val startDestination = if (loginViewModel.isLoggedIn() && !isFirstTimeUser) AppNavigationFlow.OverviewFlow.route else AppNavigationFlow.LoginFlow.route

    //App NavHost
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {

        //Login navigational flow
        navigation(route = AppNavigationFlow.LoginFlow.route, startDestination = LoginDestination.Welcome.route) {

            //Welcome screen
            composable(route = LoginDestination.Welcome.route) {
                WelcomeScreen(
                    navigateLogin = {navController.navigateSingleTopTo(LoginDestination.Login.route)},
                    navigateRegister = {navController.navigateSingleTopTo(LoginDestination.Register.route)}
                )
            }

            //login screen
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

            //Register screen
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

            //Programme screen
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

        //Overview navigational flow
        navigation(route = AppNavigationFlow.OverviewFlow.route, startDestination = MenuNavDestination.Overview.route) {

            //Overview screen
            composable(route = MenuNavDestination.Overview.route) {
                OverviewScreen(courseInformationState = courseViewModel.courseInformationState.value,
                fetchTrendingCourses = {courseViewModel.fetchTrendingCourses()},
                userCoursesStatus = userViewModel.userData.value.activeCourses,
                onViewCourse = { courseInformation -> courseViewModel.setCourseInformation(courseInformation)
                    navController.navigateSingleTopTo(AppNavigationFlow.CourseFlow.route)}
                )
            }

            //CourseOverview screen
            composable(route = MenuNavDestination.CourseOverview.route) {
                CourseOverviewScreen(programmeTopics = userViewModel.userData.value.studyProgramme.topics,
                coursesState = courseViewModel.courseInformationState.value,
                fetchAllCourses = {courseViewModel.fetchAllCourses()},
                filterCourses = {searchFilter, topicFilter -> courseViewModel.filterCourses(searchFilter, topicFilter)},
                onViewCourse = { courseInformation -> courseViewModel.setCourseInformation(courseInformation)
                    navController.navigateSingleTopTo(AppNavigationFlow.CourseFlow.route)}
                )
            }

            //NotesOverview screen
            composable(route = MenuNavDestination.NotesOverview.route) {
                NotesOverviewScreen(allNotesState = noteViewModel.allNotesState,
                saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                editNote = {id, title, noteText -> noteViewModel.updateNote(id, title, noteText)},
                deleteNote = {id -> noteViewModel.deleteNote(id)},
                filterNotes = {notes, searchFilter -> noteViewModel.filterNotes(notes, searchFilter)}
                )
            }

            //Account screen
            composable(route = MenuNavDestination.Account.route) {
                AccountScreen(userData = userViewModel.userData.value,
                userEmail = loginViewModel.getEmail(),
                fetchProgrammes = {programmeViewModel.fetchProgrammes()},
                programmeState = programmeViewModel.programmeState.value,
                updateUserName = {name -> userViewModel.updateUserName(name)},
                updateUserStudyProgramme = {programme -> userViewModel.updateUserStudyProgramme(programme)},
                updateEmail = {email -> loginViewModel.updateEmail(email)},
                resetPassword = {email -> loginViewModel.resetPassword(email)},
                deleteUser = {userViewModel.deleteUserData()
                    loginViewModel.deleteUser { navController.navigate(navController.graph.startDestinationId) }
                },
                updateState = userViewModel.updateState.value,
                resetActionState = {userViewModel.resetUserActionState()
                    loginViewModel.resetLoginActionState()},
                loginState = loginViewModel.loginState.value,
                onLogin = {email, password -> loginViewModel.login(email, password)}
                )
            }
        }

        //Course navigational flow
        navigation(route = AppNavigationFlow.CourseFlow.route, startDestination = CourseDestination.CourseDetails.route) {

            //CourseDetails screen
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

            //CourseArticle screen
            composable(route = CourseDestination.CourseArticle.route) {
                CourseArticleScreen(courseContentState = courseViewModel.courseContentState.value,
                saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)}
                )
            }

            //CourseVideo screen
            composable(route = CourseDestination.CourseVideo.route) {
                CourseVideoArticleScreen(courseContentState = courseViewModel.courseContentState.value,
                    saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                    updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)}
                )
            }

            //CourseQuiz screen
            composable(route = CourseDestination.CourseQuiz.route) {
                CourseQuizTestScreen(courseContentState = courseViewModel.courseContentState.value,
                    userCourseAnswers = {courseName -> userViewModel.getUserCourseQuizAnswers(courseName)},
                    updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)},
                    updateUserCourseQuizAnswers = {courseName, courseQuizAnswers -> userViewModel.updateUserCourseQuizAnswers(courseName, courseQuizAnswers)
                        navController.navigateSingleTopTo(CourseDestination.CourseQuizAnswers.route)}
                )
            }

            //CourseQuizAnswers screen
            composable(route = CourseDestination.CourseQuizAnswers.route) {
                CourseQuizResultsScreen(courseContentState = courseViewModel.courseContentState.value,
                    saveNote = {title, noteText -> noteViewModel.insertNote(title, noteText)},
                    userCourseAnswers = {courseName -> userViewModel.getUserCourseQuizAnswers(courseName)},
                    updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)}
                )
            }

            //CourseSummary screen
            composable(route = CourseDestination.CourseSummary.route) {
                CourseSummaryScreen(courseContentState = courseViewModel.courseContentState.value,
                    updateUserCourseSteps = {courseName, updateStepsCompleted -> userViewModel.updateUserCourseSteps(courseName, updateStepsCompleted)},
                    getUserCourseStepsCompleted = {courseName -> userViewModel.getUserCourseStepsCompleted(courseName)}
                )
            }
        }
    }
}

//NavHostController for launching single top on back stack
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