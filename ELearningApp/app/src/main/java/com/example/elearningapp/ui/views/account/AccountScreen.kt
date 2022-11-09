package com.example.elearningapp.ui.views.account

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun AccountScreen(userData: User, userEmail: String) {
    var showEditAccountDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            modifier = Modifier.size(128.dp),
            contentDescription = "No results icon",
            tint = Color.LightGray
        )
        Text(
            text = "Hello " + userData.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Name",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = userData.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Email",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = userEmail,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Study Programme",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = userData.studyProgramme.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Password",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Must be at least 8 characters long",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Button(
            modifier = Modifier.padding(end = 40.dp, bottom = 15.dp),
            onClick = {showEditAccountDialog = true}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ManageAccounts,
                    contentDescription = "Edit account",
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Edit Account")
            }
        }
    }
    if (showEditAccountDialog) {
        EditAccountDialog(userData, userEmail) { showEditAccountDialog = false }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    val user = User(name = "Student", studyProgramme = Programme("Software Technology"))

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Account", {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens, {}, MenuNavDestination.Account
                    )
                },
                content = { AccountScreen(user, "student@email.com") }
            )
        }
    }
}