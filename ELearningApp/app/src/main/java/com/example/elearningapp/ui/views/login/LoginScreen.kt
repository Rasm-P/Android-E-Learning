package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun LoginScreen(navigateRegister: () -> Unit) {
    val image: Painter = painterResource(id = R.drawable.e_learning)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Box(modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.6f),
                    painter = image,
                    contentDescription = "App Logo"
                )
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                LoginCard(navigateRegister)
            }
        }
    }
}

@Composable
fun LoginCard(navigateRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginFailed by remember { mutableStateOf(false) }
    var forgotPasswordDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        Box(modifier = Modifier.padding(30.dp)) {
            Column {
                Box(modifier = Modifier
                    .weight(1f),
                    contentAlignment = Alignment.TopCenter) {
                    Column {
                        Text(
                            text = "Login",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth(),
                            value = email,
                            onValueChange = {email = it},
                            singleLine = true,
                            trailingIcon = {
                                if (loginFailed && !forgotPasswordDialog)
                                    Icon(Icons.Filled.Error,"Login error", tint = MaterialTheme.colors.error)
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth(),
                            value = password,
                            onValueChange = {password = it},
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(Icons.Filled.Visibility, "Password visibility")
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        if (loginFailed && !forgotPasswordDialog) {
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
                            onClick = { /*TODO*/ }) {
                            Text(text = "LOGIN")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { /*TODO*/ },
                            text = "Forgot Password?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Box(modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter) {
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
                if (forgotPasswordDialog) {
                    ForgotPasswordDialog { loginFailed = !loginFailed
                        forgotPasswordDialog = !forgotPasswordDialog}
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordDialog(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var resetFailed by remember { mutableStateOf(false) }

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
                        text = "Email does not exist",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "SEND")
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