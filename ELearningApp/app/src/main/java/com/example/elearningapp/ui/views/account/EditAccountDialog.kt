package com.example.elearningapp.ui.views.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun EditAccountDialog(
    userData: User,
    userEmail: String,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(userData.name) }
    var email by remember { mutableStateOf(userEmail) }
    var studyProgramme by remember { mutableStateOf(userData.studyProgramme.name) }

    AlertDialog(
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Password",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Delete Account",
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
                        Icon(Icons.Outlined.Edit, "Edit name", tint = MaterialTheme.colors.primary)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = email,
                    onValueChange = {email = it},
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Outlined.Edit, "Edit email", tint = MaterialTheme.colors.primary)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = studyProgramme,
                    onValueChange = {studyProgramme = it},
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Outlined.ArrowDropDown, "Study programme drop down", modifier = Modifier.clickable{/*TODO*/}, tint = MaterialTheme.colors.primary)
                    },
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Change Password",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Reset password by mail",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.error,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )
            }

        },
        onDismissRequest = onDismiss,
        buttons = {
            Text(
                text = "Close",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(20.dp).clickable(onClick = onDismiss)
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun EditAccountDialogPreview() {
    val user = User(name = "Student", studyProgramme = Programme("Software Technology"))

    ELearningAppTheme {
        EditAccountDialog(user, "student@email.com") {}
    }
}