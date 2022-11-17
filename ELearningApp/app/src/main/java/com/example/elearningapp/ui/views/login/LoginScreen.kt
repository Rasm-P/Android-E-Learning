package com.example.elearningapp.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading

@Composable
fun LoginScreen(
    navigateRegister: () -> Unit,
    navigateOverview: () -> Unit,
    loginState: ActionState<String>,
    resetActionState: () -> Unit,
    onLogin: (String, String) -> Unit,
    onPasswordReset: (String) -> Unit,
    restPasswordState: ActionState<String>,
    fetchUser: () -> Unit
) {
    //Image painter
    val image: Painter = painterResource(id = R.drawable.e_learning)

    //MutableState for login failed
    var loginFailed by remember { mutableStateOf(false) }

    //Content column
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

        //Image logo
        Image(
            modifier = Modifier
                .weight(1f)
                .size(128.dp),
            painter = image,
            contentDescription = stringResource(R.string.app_logo)
        )
        //Login card
        Box(modifier = Modifier
            .weight(2f)) {
            LoginCard(navigateRegister, loginFailed, resetActionState, onLogin, onPasswordReset, restPasswordState)
        }
    }
    //When clause for login ActionState
    when(loginState) {
        is ActionState.Initial -> {}
        is ActionState.Loading -> {
            Loading()
        }
        is ActionState.Success -> {
            navigateOverview.invoke()
            fetchUser.invoke()
            resetActionState.invoke()
        }
        is ActionState.Error -> {
            loginFailed = true
            Toast.makeText(LocalContext.current, loginState.message, Toast.LENGTH_LONG).show()
            resetActionState.invoke()
        }
    }
}

@Composable
fun LoginCard(
    navigateRegister: () -> Unit,
    loginFailed: Boolean,
    resetActionState: () -> Unit,
    onLogin: (String, String) -> Unit,
    onPasswordReset: (String) -> Unit,
    restPasswordState: ActionState<String>
) {
    //MutableState for user interaction
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    //Content card
    Card(shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            //Column for login text fields
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    label = { Text(text = stringResource(R.string.email)) },
                    onValueChange = {email = it},
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
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = password,
                    label = { Text(text = stringResource(R.string.password)) },
                    onValueChange = {password = it},
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
                //If login failed error text is shown
                if (loginFailed) {
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
                //Login button
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { onLogin(email, password) }) {
                    Text(text = stringResource(R.string.login_button))
                }
                Spacer(modifier = Modifier.height(6.dp))

                //Forgot password: clickable text
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable ( onClick = {showForgotPasswordDialog = true} ),
                    text = stringResource(R.string.forgot_password),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.weight(1f))

                //Don't have an account: clickable text
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row {
                        Text(text = stringResource(R.string.dont_have_account),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = stringResource(R.string.sign_up),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier.clickable( onClick = navigateRegister ))
                    }
                }
                //Forgot password dialog
                if (showForgotPasswordDialog) {
                    ForgotPasswordDialog({ showForgotPasswordDialog = false }, resetActionState, onPasswordReset, restPasswordState)
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordDialog(onDismiss: () -> Unit, resetActionState: () -> Unit, onPasswordReset: (String) -> Unit, restPasswordState: ActionState<String>) {

    //MutableState for user interaction
    var email by remember { mutableStateOf("") }
    var resetFailed by remember { mutableStateOf(false) }
    var resetSuccess by remember { mutableStateOf(false) }

    //Alert dialog for forgot password
    AlertDialog(
        title = { Text(
            text = stringResource(R.string.forgot_password_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ) },
        text = {
            //Column text content
            Column {
                Text(
                    text = stringResource(R.string.to_reset_password),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    onValueChange = {email = it},
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.email)) },
                    placeholder = { Text(text = stringResource(R.string.email), color = Color.LightGray) },
                    trailingIcon = {
                        if (resetFailed)
                            Icon(Icons.Filled.Error,stringResource(R.string.email_error), tint = MaterialTheme.colors.error)
                    }
                )
                //Reset failed and success conditional
                if (resetFailed) {
                    Text(
                        text = stringResource(R.string.email_not_exsist),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                if (resetSuccess) {
                    Text(
                        text = stringResource(R.string.reset_mail_sendt),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Green,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { onPasswordReset(email) }) {
                    Text(text = stringResource(R.string.send))
                }
            }
            //When clause for reset password state
            when(restPasswordState) {
                is ActionState.Initial -> {}
                is ActionState.Loading -> {
                    Loading()
                }
                is ActionState.Success -> {
                    email = ""
                    resetFailed = false
                    resetSuccess = true
                    resetActionState.invoke()
                }
                is ActionState.Error -> {
                    resetSuccess = false
                    resetFailed = true
                    Toast.makeText(LocalContext.current, restPasswordState.message, Toast.LENGTH_LONG).show()
                    resetActionState.invoke()
                }
            }
        },
        onDismissRequest = onDismiss,
        buttons = {
            //Close dialog
            Text(
                text = stringResource(R.string.close),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(20.dp).clickable( onClick = onDismiss )
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ELearningAppTheme {
        LoginScreen({},{},ActionState.Initial,{},{ _, _ ->},{},ActionState.Initial,{})
    }
}
