package com.example.elearningapp.ui.views.account

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.ProgrammeData
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun AccountScreen(
    userData: User,
    userEmail: String,
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>,
    updateUserName: (String) -> Unit,
    updateUserStudyProgramme: (Programme) -> Unit,
    updateEmail: (String) -> Unit,
    resetPassword: (String) -> Unit,
    deleteUser: () -> Unit,
    updateState: ActionState<String>,
    resetActionState: () -> Unit,
    loginState: ActionState<String>,
    onLogin: (String, String) -> Unit
) {
    //MutableState for EditAccountDialog
    var showEditAccountDialog by remember { mutableStateOf(false) }

    //Account screen content column
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            modifier = Modifier.size(128.dp),
            contentDescription = stringResource(R.string.account_icon_description),
            tint = Color.LightGray
        )
        Text(
            text = stringResource(R.string.hello) + userData.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))

        //Account information card
        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp) {
            Column(modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)) {
                Text(
                    text = stringResource(R.string.name),
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.email),
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.study_programme),
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.password),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(R.string.password_must_be),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }

    //Edit account button box
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Button(
            modifier = Modifier.padding(end = 40.dp, bottom = 15.dp),
            onClick = {showEditAccountDialog = true}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ManageAccounts,
                    contentDescription = stringResource(R.string.edit_account),
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(R.string.edit_account))
            }
        }
    }

    //Edit account dialog conditional
    if (showEditAccountDialog) {
        EditAccountDialog(userData, userEmail, { showEditAccountDialog = false }, fetchProgrammes, programmeState, updateUserName, updateUserStudyProgramme, updateEmail, resetPassword, deleteUser, resetActionState, loginState, onLogin)
    }

    //ActionState Toasts for Success or Error
    if (updateState is ActionState.Success) {
        Toast.makeText(LocalContext.current, updateState.data, Toast.LENGTH_SHORT).show()
        resetActionState.invoke()
    } else if (loginState is ActionState.Success) {
        Toast.makeText(LocalContext.current, loginState.data, Toast.LENGTH_SHORT).show()
    } else if (updateState is ActionState.Error) {
        Toast.makeText(LocalContext.current, updateState.message, Toast.LENGTH_SHORT).show()
        resetActionState.invoke()
    } else if (loginState is ActionState.Error) {
        Toast.makeText(LocalContext.current, loginState.message, Toast.LENGTH_SHORT).show()
        resetActionState.invoke()
    }
}


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
                topBar = { TopBar("Account", {}, {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens, {}, MenuNavDestination.Account
                    )
                }
            ) {
                innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                    AccountScreen(user, "student@email.com",{},ActionState.Success(ProgrammeData.programmes),{},{},{},{},{}, ActionState.Initial, {}, ActionState.Initial,{_,_->})
                }
            }
        }
    }
}