package com.example.elearningapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.elearningapp.ui.views.account.AccountScreen
import com.example.elearningapp.ui.views.courses.CourseOverviewScreen
import com.example.elearningapp.ui.views.login.LoginScreen
import com.example.elearningapp.ui.views.login.ProgrammeScreen
import com.example.elearningapp.ui.views.login.RegisterScreen
import com.example.elearningapp.ui.views.login.WelcomeScreen
import com.example.elearningapp.ui.views.notes.NotesOverviewScreen
import com.example.elearningapp.ui.views.overview.OverviewScreen

// Built with inspiration from: https://developer.android.com/codelabs/jetpack-compose-navigation#0

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = AppNavigationFlow.LoginFlow.route, modifier = modifier) {
        navigation(route = AppNavigationFlow.LoginFlow.route, startDestination = LoginDestination.Welcome.route) {
            composable(route = LoginDestination.Welcome.route) {
                WelcomeScreen(
                    navigateLogin = {navController.navigateSingleTopTo(LoginDestination.Login.route)},
                    navigateRegister = {navController.navigateSingleTopTo(LoginDestination.Register.route)}
                )
            }
            composable(route = LoginDestination.Login.route) {
                LoginScreen(
                    navigateRegister = {navController.navigateSingleTopTo(LoginDestination.Register.route)}
                )
            }
            composable(route = LoginDestination.Register.route) {
                RegisterScreen(
                    navigateLogin = {navController.navigateSingleTopTo(LoginDestination.Login.route)}
                )
            }
            composable(route = LoginDestination.Programme.route) {
                ProgrammeScreen()
            }
        }
        navigation(route = AppNavigationFlow.OverviewFlow.route, startDestination = MenuNavDestination.Overview.route) {
            composable(route = MenuNavDestination.Overview.route) {
                OverviewScreen()
            }
            composable(route = MenuNavDestination.CourseOverview.route) {
                CourseOverviewScreen()
            }
            composable(route = MenuNavDestination.NotesOverview.route) {
                NotesOverviewScreen()
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