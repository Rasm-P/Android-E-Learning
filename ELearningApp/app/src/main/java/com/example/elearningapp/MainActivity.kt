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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.elearningapp.ui.theme.ELearningAppTheme
import androidx.navigation.compose.rememberNavController
import com.example.elearningapp.navigation.AppNavHost
import com.example.elearningapp.navigation.Overview
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.navigation.navigateSingleTopTo
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.viewmodels.LoginViewModel
import com.example.elearningapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun ELearningApp(loginViewModel: LoginViewModel, userViewModel: UserViewModel) {
    ELearningAppTheme {
        val navController = rememberNavController()
        val navBackStackEntry  by navController.currentBackStackEntryAsState()
        val currentDestination = bottomNavScreens.find { it.route == navBackStackEntry?.destination?.route } ?: Overview

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(bottomBar = { BottomNavBar(screens = bottomNavScreens, onSelected = { screen -> navController.navigateSingleTopTo(screen.route)}, currentDestination = currentDestination) }) {
               innerPadding -> AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}