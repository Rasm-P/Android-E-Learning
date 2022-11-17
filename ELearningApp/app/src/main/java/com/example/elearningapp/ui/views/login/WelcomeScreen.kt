package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun WelcomeScreen(navigateLogin: () -> Unit, navigateRegister: () -> Unit) {

    //Image painter
    val image: Painter = painterResource(id = R.drawable.e_learning)

    //Content column
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        //Image logo
        Image(
            modifier = Modifier
                .weight(1f)
                .size(192.dp),
            painter = image,
            contentDescription = stringResource(R.string.app_logo)
        )
        //Content box for card
        Box(modifier = Modifier
            .weight(1f)) {
            WelcomeCard(navigateLogin, navigateRegister)
        }
    }
}

@Composable
fun WelcomeCard(navigateLogin: () -> Unit, navigateRegister: () -> Unit) {

    //Content card
    Card(shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {

        //Content column
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)) {
            Text(
                text = stringResource(R.string.welcome),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.e_learning_app),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1f))

            //Box for login and sign up buttons
            Box(modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(modifier = Modifier
                        .fillMaxWidth().height(40.dp),
                        onClick = navigateLogin) {
                        Text(text = stringResource(R.string.login_button))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = stringResource(R.string.dont_have_account),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth().height(40.dp),
                        onClick = navigateRegister,
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                        border = BorderStroke(1.dp, color = MaterialTheme.colors.primary)) {
                        Text(text = stringResource(R.string.sign_up), color = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ELearningAppTheme {
        WelcomeScreen({},{})
    }
}
