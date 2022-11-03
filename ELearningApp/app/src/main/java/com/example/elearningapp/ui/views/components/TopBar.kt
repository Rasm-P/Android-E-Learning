package com.example.elearningapp.ui.views.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.navigation.loginNavScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    route: String,
    onBackPressed: () -> Unit,
    onLogoutPressed: () -> Unit,
    onAccountPressed: () -> Unit
) {
    val isRouteInLoginFLow = loginNavScreens.any { screen -> screen.route == route }
    val isRouteInOverviewFLow = bottomNavScreens.any { screen -> screen.route == route }

    CenterAlignedTopAppBar(
        colors = if (!isRouteInLoginFLow) TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colors.primary, titleContentColor = MaterialTheme.colors.secondary) else TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colors.background),
        title = {
            if (!isRouteInLoginFLow) {
                Text(
                    text = routeAsTitle(route),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (isRouteInOverviewFLow) {
                IconButton(onClick = onLogoutPressed) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            } else {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        },
        actions = {
            if (!isRouteInLoginFLow) {
                IconButton(onClick = onAccountPressed) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Account",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    )
}

fun routeAsTitle(destination: String) : String {
    return destination.replace("-", " ").split(" ").map { it.capitalize() }.joinToString(" ")
}