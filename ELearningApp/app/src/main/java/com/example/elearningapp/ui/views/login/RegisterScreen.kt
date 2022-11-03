package com.example.elearningapp.ui.views.login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading

@Composable
fun RegisterScreen(
    navigateLogin: () -> Unit,
    navigateProgramme: () -> Unit,
    loginState: ActionState<Boolean>,
    resetActionState: () -> Unit,
    onRegister: (String, String) -> Unit,
    setFirstTimeUser: () -> Unit
) {
    val image: Painter = painterResource(id = R.drawable.e_learning)
    var registerFailed by remember { mutableStateOf(false) }

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
            RegisterCard(navigateLogin, registerFailed, {registerFailed = false}, onRegister, setFirstTimeUser)
        }
    }
    when(loginState) {
        is ActionState.Initial -> {}
        is ActionState.Loading -> {
            Loading()
        }
        is ActionState.Success -> {
            if (loginState.data) {
                navigateProgramme.invoke()
            }
            resetActionState.invoke()
        }
        is ActionState.Error -> {
            registerFailed = true
            Toast.makeText(LocalContext.current, loginState.message, Toast.LENGTH_LONG).show()
            resetActionState.invoke()
        }
    }
}

@Composable
fun RegisterCard(
    navigateLogin: () -> Unit,
    registerFailed: Boolean,
    setRegisterFailed: () -> Unit,
    onRegister: (String, String) -> Unit,
    setFirstTimeUser: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
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
                    onValueChange = { email = it },
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    label = { Text(text = "Password") },
                    onValueChange = { password = it },
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = repeatPassword,
                    label = { Text(text = "Repeat Password") },
                    onValueChange = { repeatPassword = it },
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
                        text = if (password != repeatPassword) "Passwords were not the same!" else "Email or password was not valid!",
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
                    onClick = {
                        if (password == repeatPassword) {
                            setFirstTimeUser.invoke()
                            onRegister(email, password)
                        } else {
                            setRegisterFailed.invoke()
                        }
                    }
                ) {
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


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ELearningAppTheme {
        RegisterScreen({},{},ActionState.Initial,{},{ _, _ ->},{})
    }
}
