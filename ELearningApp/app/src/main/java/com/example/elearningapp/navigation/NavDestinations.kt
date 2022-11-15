package com.example.elearningapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuNavDestination(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    object Overview : MenuNavDestination (
        "Overview",
        Icons.Filled.Home,
        "overview"
    )
    object CourseOverview : MenuNavDestination (
        "Courses",
        Icons.Filled.School,
        "courses"
    )
    object NotesOverview : MenuNavDestination (
        "Notes",
        Icons.Filled.StickyNote2,
        "notes"
    )
    object Account : MenuNavDestination (
        "Account",
        Icons.Filled.Person,
        "account"
    )
}

sealed class LoginDestination(
    val route: String
) {
    object Welcome : LoginDestination("welcome")
    object Login : LoginDestination("login")
    object Register : LoginDestination("register")
    object Programme : LoginDestination("programme")
}

sealed class CourseDestination(
    val route: String
) {
    object CourseDetails : CourseDestination("course-details")
    object CourseArticle : CourseDestination("article")
    object CourseVideo : CourseDestination("video-article")
    object CourseQuiz : CourseDestination("quiz-test")
    object CourseQuizAnswers : CourseDestination("quiz-results")
    object CourseSummary : CourseDestination("course-summary")
}

sealed class AppNavigationFlow(
    val route: String
) {
    object LoginFlow : AppNavigationFlow("login-flow")
    object OverviewFlow : AppNavigationFlow("overview-flow")
    object CourseFlow : AppNavigationFlow("course-flow")
}

val bottomNavScreens = listOf(MenuNavDestination.Overview, MenuNavDestination.CourseOverview, MenuNavDestination.NotesOverview, MenuNavDestination.Account)

val loginNavScreens = listOf(LoginDestination.Welcome, LoginDestination.Login, LoginDestination.Register, LoginDestination.Programme)

val courseNavScreens = listOf(CourseDestination.CourseDetails,CourseDestination.CourseArticle,CourseDestination.CourseVideo,CourseDestination.CourseQuiz,CourseDestination.CourseQuizAnswers,CourseDestination.CourseSummary)