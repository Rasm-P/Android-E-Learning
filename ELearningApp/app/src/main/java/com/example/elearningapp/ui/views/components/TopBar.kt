package com.example.elearningapp.ui.views.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.example.elearningapp.navigation.LoginDestination
import com.example.elearningapp.navigation.bottomNavScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(route: String, onBackPressed: () -> Unit, onLogoutPressed: () -> Unit) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = routeAsTitle(route),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (bottomNavScreens.any { screen -> screen.route == route }) {
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
        }
    )
}

fun routeAsTitle(destination: String) : String {
    return destination.replace("-", " ").split(" ").map { it.capitalize() }.joinToString(" ")
}