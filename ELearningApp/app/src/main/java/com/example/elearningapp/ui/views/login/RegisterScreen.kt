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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R

@Composable
fun RegisterScreen(navigateLogin: () -> Unit) {
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
                RegisterCard(navigateLogin)
            }
        }
    }
}

@Composable
fun RegisterCard(navigateLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var registerFailed by remember { mutableStateOf(false) }

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
                            text = "Sign Up",
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
                                if (registerFailed)
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
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Repeat Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth(),
                            value = repeatPassword,
                            onValueChange = {repeatPassword = it},
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
                        if (registerFailed) {
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
                            Text(text = "SIGN UP")
                        }
                    }
                }
                Box(modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter) {
                    Row {
                        Text(text = "Already a user?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "LOGIN",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier.clickable( onClick = navigateLogin )
                        )
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            RegisterScreen()
        }
    }
}
*/