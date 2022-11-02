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
import com.example.elearningapp.viewmodels.LoginViewModel
import com.example.elearningapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.elearningapp.ui.views.components.TopBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ELearningApp(loginViewModel, userViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ELearningApp(loginViewModel: LoginViewModel, userViewModel: UserViewModel) {
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
                topBar = { if (currentRoute != LoginDestination.Welcome.route) TopBar(route = currentRoute, onBackPressed = {navController.popBackStack()}, onLogoutPressed = { navController.navigate(AppNavigationFlow.LoginFlow.route); loginViewModel.logout(); } ) {navController.navigateSingleTopTo(AppNavigationFlow.OverviewFlow.route)} },
                bottomBar = { if (bottomNavDestination is MenuNavDestination) BottomNavBar(screens = bottomNavScreens, onSelected = { screen -> navController.navigateSingleTopTo(screen.route)}, currentDestination = bottomNavDestination) }
            )
            {
               innerPadding -> AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding), loginViewModel, userViewModel)
            }
        }
    }
}