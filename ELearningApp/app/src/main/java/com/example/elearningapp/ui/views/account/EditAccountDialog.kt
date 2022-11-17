package com.example.elearningapp.ui.views.account

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.ProgrammeData
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading

// Dropdown menu built with inspiration from: https://www.geeksforgeeks.org/drop-down-menu-in-android-using-jetpack-compose/

@Composable
fun EditAccountDialog(
    userData: User,
    userEmail: String,
    onDismiss: () -> Unit,
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>,
    updateUserName: (String) -> Unit,
    updateUserStudyProgramme: (Programme) -> Unit,
    updateEmail: (String) -> Unit,
    resetPassword: (String) -> Unit,
    deleteUser: () -> Unit,
    resetActionState: () -> Unit,
    loginState: ActionState<String>,
    onLogin: (String, String) -> Unit
) {
    //MutableState for user interaction
    var name by remember { mutableStateOf(userData.name) }
    var email by remember { mutableStateOf(userEmail) }
    var studyProgramme by remember { mutableStateOf(userData.studyProgramme) }

    //Drop down menu variables
    var dropdownExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(Size.Zero) }

    //MutableState for authenticationRequestDialog
    var authenticationRequestDialog by remember { mutableStateOf(false) }

    //Mutable state for delete, update and reset actions
    var deleteAccount by remember { mutableStateOf(false) }
    var emailUpdate by remember { mutableStateOf(false) }
    var passwordReset by remember { mutableStateOf(false) }

    //Fetches available studyProgrammes
    LaunchedEffect(Unit, block = {
        fetchProgrammes.invoke()
    })

    //EditAccountDialog
    AlertDialog(
        text = {
            //Column content for dialog text
            Column(modifier = Modifier.fillMaxWidth()) {
                //Top of dialog text row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.password),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        modifier = Modifier.clickable(onClick = {
                            authenticationRequestDialog = true
                            deleteAccount = true
                        }),
                        text = stringResource(R.string.delete_account),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = name,
                    onValueChange = {name = it},
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Outlined.Edit, stringResource(R.string.edit_name), tint = MaterialTheme.colors.primary)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    onValueChange = {email = it},
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Outlined.Edit, stringResource(R.string.edit_email), tint = MaterialTheme.colors.primary)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                //Box for dropdown menu
                Box {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                dropdownWidth = coordinates.size.toSize()
                            },
                        value = studyProgramme.name,
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                if (dropdownExpanded) Icons.Outlined.ArrowDropUp else Icons.Outlined.ArrowDropDown,
                                stringResource(R.string.dropdown_icon_description),
                                modifier = Modifier.clickable {
                                    dropdownExpanded = !dropdownExpanded
                                },
                                tint = MaterialTheme.colors.primary
                            )
                        },
                    )
                    if (programmeState is ActionState.Success) {
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { dropdownWidth.width.toDp() })
                                .requiredSizeIn(maxHeight = 230.dp),
                        ) {
                            programmeState.data.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    studyProgramme = label
                                    dropdownExpanded = false
                                }) {
                                    Text(text = label.name)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                //Reset password text
                Text(
                    text = stringResource(R.string.change_password),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(modifier = Modifier.clickable(onClick = {
                    authenticationRequestDialog = true
                    passwordReset = true
                }),
                    text = stringResource(R.string.reset_password_by_mail),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.error,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )
            }

        },
        onDismissRequest = onDismiss,
        buttons = {
            //Row for bottom buttons
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.close),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable(onClick = onDismiss)
                )
                Text(
                    text = stringResource(R.string.save),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable(onClick = {
                            //onClick save conditionals
                            if (name != userData.name) {
                                updateUserName(name)
                            }
                            if (email != userEmail) {
                                authenticationRequestDialog = true
                                emailUpdate = true
                            }
                            if (studyProgramme != userData.studyProgramme) {
                                updateUserStudyProgramme(studyProgramme)
                            }
                        })
                )
            }
        }
    )
    //AuthenticationRequestDialog conditional
    if (authenticationRequestDialog) {
        AuthenticationRequestDialog(
            {authenticationRequestDialog = false },
            onLogin,
            loginState,
            {if (deleteAccount) {
                onDismiss.invoke()
                deleteUser.invoke()
                deleteAccount = false
            }},
            {if (emailUpdate) {
                updateEmail(email)
                emailUpdate = false
            }},
            {if (passwordReset) {
                resetPassword(userEmail)
                passwordReset = false
            }}
        )
    } else {
        resetActionState.invoke()
    }
}


@Composable
fun AuthenticationRequestDialog(
    onDismiss: () -> Unit,
    onLogin: (String, String) -> Unit,
    loginState: ActionState<String>,
    deleteUser: () -> Unit,
    updateEmail: () -> Unit,
    resetPassword: () -> Unit
) {
    //MutableState for user interaction
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var authenticationFailed by remember { mutableStateOf(false) }

    //AuthenticationRequestDialog
    AlertDialog(
        title = {
            //Title box
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.authentication_required),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        text = {
            //Dialog content column
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.in_order_to_perform),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    label = { Text(text = stringResource(R.string.email)) },
                    onValueChange = { email = it },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            email = ""
                        }) {
                            if (email != "") {
                                Icon(Icons.Filled.Clear, stringResource(R.string.clear_email))
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    label = { Text(text = stringResource(R.string.password)) },
                    onValueChange = { password = it },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            if (passwordVisible) {
                                Icon(Icons.Filled.Visibility, stringResource(R.string.password_visible))
                            } else {
                                Icon(Icons.Filled.VisibilityOff, stringResource(R.string.password_not_visible))
                            }
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                //Text for authenticationFailed conditional
                if (authenticationFailed) {
                    Text(
                        text = stringResource(R.string.wrong_email_or_password),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                } else {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                Button(onClick = { onLogin(email,password) }) {
                    Text(text = stringResource(R.string.submit))
                }    
            }
            //ActionState when clause
            when(loginState) {
                is ActionState.Initial -> {}
                is ActionState.Loading -> {
                    Loading()
                }
                is ActionState.Success -> {
                    resetPassword.invoke()
                    updateEmail.invoke()
                    deleteUser.invoke()
                    onDismiss.invoke()
                }
                is ActionState.Error -> {
                    authenticationFailed = true
                    Toast.makeText(LocalContext.current, loginState.message, Toast.LENGTH_LONG).show()
                }
            }
        },
        //Close button
        onDismissRequest = onDismiss,
        buttons = { Text(
            text = stringResource(R.string.close),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(20.dp).clickable(onClick = onDismiss)
        )}
    )
}


@Preview(showBackground = true)
@Composable
fun EditAccountDialogPreview() {
    val user = User(name = "Student", studyProgramme = Programme("Software Technology"))

    ELearningAppTheme {
        EditAccountDialog(user, "student@email.com",{},{},ActionState.Success(ProgrammeData.programmes),{},{},{},{},{}, {}, ActionState.Initial, {_,_->})
    }
}