package com.example.elearningapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

interface NavDestination {
    val icon: ImageVector
    val route: String
}

object Overview : NavDestination {
    override val icon = Icons.Filled.Home
    override val route = "overview"
}

object CourseOverview : NavDestination {
    override val icon = Icons.Filled.Home
    override val route = "course-overview"
}

object NotesOverview : NavDestination {
    override val icon = Icons.Filled.Home
    override val route = "notes-overview"
}

object Account : NavDestination {
    override val icon = Icons.Filled.Home
    override val route = "account"
}

val bottomNavScreens = listOf(Overview, CourseOverview, NotesOverview, Account)