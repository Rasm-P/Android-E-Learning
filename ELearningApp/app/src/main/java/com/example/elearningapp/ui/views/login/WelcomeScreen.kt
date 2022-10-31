package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R

@Composable
fun WelcomeScreen(navigateLogin: () -> Unit, navigateRegister: () -> Unit) {
    val image: Painter = painterResource(id = R.drawable.e_learning)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Box(modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.6f),
                    painter = image,
                    contentDescription = "App Logo"
                )
            }
            Box(modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                WelcomeCard(navigateLogin, navigateRegister)
            }
        }
    }
}

@Composable
fun WelcomeCard(navigateLogin: () -> Unit, navigateRegister: () -> Unit) {
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
                            text = "Welcome!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Box(modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter) {
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(modifier = Modifier
                            .fillMaxWidth().height(40.dp),
                            onClick = navigateLogin) {
                            Text(text = "LOGIN")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Don't have an account?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(modifier = Modifier
                            .fillMaxWidth().height(40.dp),
                            onClick = navigateRegister,
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                            border = BorderStroke(1.dp, color = MaterialTheme.colors.primary)) {
                            Text(text = "SIGN UP", color = MaterialTheme.colors.primary)
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            WelcomeScreen()
        }
    }
}
*/