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
        "course-overview"
    )
    object NotesOverview : MenuNavDestination (
        "Notes",
        Icons.Filled.StickyNote2,
        "notes-overview"
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
    object CourseOverview : CourseDestination("course-overview")
    object CourseArticle : CourseDestination("course-article")
    object CourseVideo : CourseDestination("course-video")
    object CourseQuiz : CourseDestination("course-quiz")
    object CourseQuizAnswers : CourseDestination("course-quiz-answers")
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