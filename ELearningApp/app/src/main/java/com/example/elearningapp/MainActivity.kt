package com.example.elearningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.elearningapp.ui.theme.ELearningAppTheme
import androidx.navigation.compose.rememberNavController
import com.example.elearningapp.navigation.*
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import dagger.hilt.android.AndroidEntryPoint
import com.example.elearningapp.ui.views.components.TopBar
import com.example.elearningapp.viewmodels.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //App viewModels
    private val loginViewModel: LoginViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val programmeViewModel: ProgrammeViewModel by viewModels()
    private val courseViewModel: CourseViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //App compose content
        setContent {
            ELearningApp(loginViewModel, userViewModel, programmeViewModel, courseViewModel, noteViewModel)
        }
    }
}

@Composable
fun ELearningApp(loginViewModel: LoginViewModel, userViewModel: UserViewModel, programmeViewModel: ProgrammeViewModel, courseViewModel: CourseViewModel, noteViewModel: NoteViewModel) {
    ELearningAppTheme {

        //Values for navController and the current route destination
        val navController = rememberNavController()
        val navBackStackEntry  by navController.currentBackStackEntryAsState()
        val bottomNavDestination = bottomNavScreens.find { it.route == navBackStackEntry?.destination?.route }
        val currentRoute = navBackStackEntry?.destination?.route ?: LoginDestination.Welcome.route

        //System status and navigation bar colors
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = if(isRouteInLoginNavScreens(currentRoute)) MaterialTheme.colors.background
            else MaterialTheme.colors.primary
        )
        systemUiController.setNavigationBarColor(
            color = if (currentRoute == CourseDestination.CourseDetails.route) MaterialTheme.colors.background
            else if(isRouteInCourseNavScreens(currentRoute)) MaterialTheme.colors.primary
            else MaterialTheme.colors.secondary
        )

        //App surface
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = {
                    //App TopBar
                    if (currentRoute != LoginDestination.Welcome.route && currentRoute != LoginDestination.Programme.route)
                    TopBar(
                        route = currentRoute,
                        onBackPressed = {navController.popBackStack()},
                        onLogoutPressed = { navController.navigate(navController.graph.startDestinationId); userViewModel.resetUserActionState(); loginViewModel.logout(); },
                        onAccountPressed = {navController.navigateSingleTopTo(MenuNavDestination.Account.route)},
                        onCourseDetailsPressed = {navController.navigateSingleTopTo(CourseDestination.CourseDetails.route)})
                         },
                bottomBar = {
                    //App BottomNavBar and CourseBottomNavBar conditional
                    if (bottomNavDestination is MenuNavDestination)
                        BottomNavBar(
                            screens = bottomNavScreens,
                            onSelected = { screen -> navController.navigateSingleTopTo(screen.route) },
                            currentDestination = bottomNavDestination
                        )
                    else if (isRouteInCourseNavScreens(currentRoute) && currentRoute != CourseDestination.CourseDetails.route) {
                        val currentStepIndex = courseNavScreens.indexOfFirst { screen -> screen.route == currentRoute }
                        CourseBottomNavBar(
                            onPreviousPressed = {navController.navigateSingleTopTo(courseNavScreens[currentStepIndex-1].route)},
                            onNextPressed = {navController.navigateSingleTopTo(courseNavScreens[currentStepIndex+1].route)},
                            onFinishedPressed = {navController.navigateSingleTopTo(MenuNavDestination.Overview.route)},
                            currentRoute = currentRoute
                        )
                    }
                }
            )
            {
               //App NavHost
               innerPadding -> AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding), loginViewModel, userViewModel, programmeViewModel, courseViewModel, noteViewModel)
            }
        }
    }
}