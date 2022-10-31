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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.ui.views.components.Loading
import com.example.elearningapp.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navigateRegister: () -> Unit, navigateOverview: () -> Unit, loginViewModel: LoginViewModel) {
    val image: Painter = painterResource(id = R.drawable.e_learning)
    var loginFailed by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Image(
            modifier = Modifier
                .weight(1f)
                .size(128.dp),
            painter = image,
            contentDescription = "App Logo"
        )
        Box(modifier = Modifier
            .weight(2f)) {
            LoginCard(navigateRegister, loginFailed, loginViewModel)
        }
    }
    when(val value = loginViewModel.loginState.value) {
        is ActionState.Initial -> {}
        is ActionState.Loading -> {
            Loading()
        }
        is ActionState.Success -> {
            if (value.data) {
                navigateOverview.invoke()
            }
            loginViewModel.resetLoginState()
        }
        is ActionState.Error -> {
            loginFailed = true
            Toast.makeText(LocalContext.current, value.message, Toast.LENGTH_LONG).show()
            loginViewModel.resetLoginState()
        }
    }
}

@Composable
fun LoginCard(
    navigateRegister: () -> Unit,
    loginFailed: Boolean,
    loginViewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    Card(shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    label = { Text(text = "Email") },
                    onValueChange = {email = it},
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            email = ""
                        }) {
                            if (email != "") {
                                Icon(Icons.Filled.Clear, "Clear email")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = password,
                    label = { Text(text = "Password") },
                    onValueChange = {password = it},
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            if (passwordVisible) {
                                Icon(Icons.Filled.Visibility, "Password visible")
                            } else {
                                Icon(Icons.Filled.VisibilityOff, "Password not visible")
                            }
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                if (loginFailed) {
                    Text(
                        text = "Wrong email or password!",
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
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { loginViewModel.login(email, password) }) {
                    Text(text = "LOGIN")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable ( onClick = {showForgotPasswordDialog = true} ),
                    text = "Forgot Password?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row {
                        Text(text = "Don't have an account?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "SIGN UP",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier.clickable( onClick = navigateRegister ))
                    }
                }
                if (showForgotPasswordDialog) {
                    ForgotPasswordDialog({ showForgotPasswordDialog = !showForgotPasswordDialog }, loginViewModel)
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordDialog(onDismiss: () -> Unit, loginViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var resetFailed by remember { mutableStateOf(false) }
    var resetSuccess by remember { mutableStateOf(false) }

    AlertDialog(
        title = { Text(
            text = "Reset Password",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ) },
        text = {
            Column() {
                Text(
                    text = "To reset your password, please enter your account email below.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    onValueChange = {email = it},
                    singleLine = true,
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Email", color = Color.LightGray) },
                    trailingIcon = {
                        if (resetFailed)
                            Icon(Icons.Filled.Error,"Login error", tint = MaterialTheme.colors.error)
                    }
                )
                if (resetFailed) {
                    Text(
                        text = "Email does not exist!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                if (resetSuccess) {
                    Text(
                        text = "Password reset email sent!",
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
                    onClick = { loginViewModel.resetPassword(email) }) {
                    Text(text = "SEND")
                }
            }
            when(val value = loginViewModel.resetState.value) {
                is ActionState.Initial -> {}
                is ActionState.Loading -> {
                    Loading()
                }
                is ActionState.Success -> {
                    if (value.data) {
                        email = ""
                        resetFailed = false
                        resetSuccess = true
                    }
                    loginViewModel.resetLoginState()
                }
                is ActionState.Error -> {
                    resetSuccess = false
                    resetFailed = true
                    Toast.makeText(LocalContext.current, value.message, Toast.LENGTH_LONG).show()
                    loginViewModel.resetLoginState()
                }
            }
        },
        onDismissRequest = onDismiss,
        buttons = {
            Text(
                text = "Close",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(12.dp).clickable( onClick = onDismiss )
            )
        }
    )
}

/*
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            LoginScreen()
        }
    }
}
*/