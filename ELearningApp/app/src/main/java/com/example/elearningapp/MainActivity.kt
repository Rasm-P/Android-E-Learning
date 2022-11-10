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
import dagger.hilt.android.AndroidEntryPoint
import com.example.elearningapp.ui.views.components.TopBar
import com.example.elearningapp.viewmodels.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val programmeViewModel: ProgrammeViewModel by viewModels()
    private val courseViewModel: CourseViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ELearningApp(loginViewModel, userViewModel, programmeViewModel, courseViewModel, noteViewModel)
        }
    }
}

@Composable
fun ELearningApp(loginViewModel: LoginViewModel, userViewModel: UserViewModel, programmeViewModel: ProgrammeViewModel, courseViewModel: CourseViewModel, noteViewModel: NoteViewModel) {
    ELearningAppTheme {
        val navController = rememberNavController()
        val navBackStackEntry  by navController.currentBackStackEntryAsState()
        val bottomNavDestination = bottomNavScreens.find { it.route == navBackStackEntry?.destination?.route }
        val currentRoute = navBackStackEntry?.destination?.route ?: LoginDestination.Welcome.route

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { if (currentRoute != LoginDestination.Welcome.route && currentRoute != LoginDestination.Programme.route) TopBar(route = currentRoute, onBackPressed = {navController.popBackStack()}, onLogoutPressed = { navController.navigate(navController.graph.startDestinationId); loginViewModel.logout(); } ) {navController.navigateSingleTopTo(MenuNavDestination.Account.route)} },
                bottomBar = { if (bottomNavDestination is MenuNavDestination) BottomNavBar(screens = bottomNavScreens, onSelected = { screen -> navController.navigateSingleTopTo(screen.route)}, currentDestination = bottomNavDestination) }
            )
            {
               innerPadding -> AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding), loginViewModel, userViewModel, programmeViewModel, courseViewModel, noteViewModel)
            }
        }
    }
}