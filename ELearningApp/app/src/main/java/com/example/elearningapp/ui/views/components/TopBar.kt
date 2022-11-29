package com.example.elearningapp.ui.views.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.elearningapp.R
import com.example.elearningapp.navigation.*
import com.example.elearningapp.ui.theme.ELearningAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    route: String,
    onBackPressed: () -> Unit,
    onLogoutPressed: () -> Unit,
    onAccountPressed: () -> Unit,
    onCourseDetailsPressed: () -> Unit
) {
    //Boolean values to determine content depending on the current route
    val isRouteInLoginFLow = isRouteInLoginNavScreens(route)
    val isRouteInOverviewFLow = isRouteInBottomNavScreens(route)
    val isRouteInCourseFlow = isRouteInCourseNavScreens(route) && route != CourseDestination.CourseDetails.route

    //App TopBar content
    CenterAlignedTopAppBar(
        colors = if (!isRouteInLoginFLow) TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colors.primary, titleContentColor = MaterialTheme.colors.secondary) else TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colors.background),
        title = {
            //Title conditional
            if (!isRouteInLoginFLow) {
                Text(
                    text = routeAsTitle(route),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            //Left icon conditionals
            if (isRouteInOverviewFLow) {
                IconButton(onClick = onLogoutPressed) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = stringResource(R.string.logout),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            } else if (isRouteInCourseFlow) {
                IconButton(onClick = onCourseDetailsPressed) {
                    Icon(
                        imageVector = Icons.Filled.FormatListBulleted,
                        contentDescription = stringResource(R.string.to_course_details),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            } else {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = if (isRouteInLoginFLow) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    )
                }
            }
        },
        actions = {
            //Right icon conditionals
            if (!isRouteInLoginFLow && route != MenuNavDestination.Account.route && isRouteInOverviewFLow) {
                IconButton(onClick = onAccountPressed) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = stringResource(R.string.account),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ELearningAppTheme {
        TopBar("overview", {}, {}, {}, {})
    }
}

//Function for using route as screen title
fun routeAsTitle(destination: String) : String {
    return destination.replace("-", " ").split(" ")
        .joinToString(" ") { it -> it.replaceFirstChar { it.uppercase() } }
}