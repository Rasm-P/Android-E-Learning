package com.example.elearningapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

interface NavDestination {
    val label: String
    val icon: ImageVector
    val route: String
}

object Overview : NavDestination {
    override val label = "Overview"
    override val icon = Icons.Filled.Home
    override val route = "overview"
}

object CourseOverview : NavDestination {
    override val label = "Courses"
    override val icon = Icons.Filled.School
    override val route = "course-overview"
}

object NotesOverview : NavDestination {
    override val label = "Notes"
    override val icon = Icons.Filled.StickyNote2
    override val route = "notes-overview"
}

object Account : NavDestination {
    override val label = "Account"
    override val icon = Icons.Filled.Person
    override val route = "account"
}

val bottomNavScreens = listOf(Overview, CourseOverview, NotesOverview, Account)