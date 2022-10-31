package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
                RegisterCard(navigateLogin)
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

    Card(shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)) {
            Text(
                text = "Sign Up",
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
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = repeatPassword,
                    label = { Text(text = "Repeat Password") },
                    onValueChange = {repeatPassword = it},
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
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row {
                        Text(
                            text = "Already a user?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LOGIN",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier.clickable(onClick = navigateLogin)
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