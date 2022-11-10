package com.example.elearningapp.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.ProgrammeData.programmes
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading

@Composable
fun ProgrammeScreen(
    navigateOverview: () -> Unit,
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>,
    setFirstTimeUser: () -> Unit,
    addUserData: (String,Programme) -> Unit,
    userState: ActionState<User>,
    resetActionState: () -> Unit
) {
    val image: Painter = painterResource(id = R.drawable.e_learning)
    var addUserDataFailed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        fetchProgrammes.invoke()
    })

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Image(
            modifier = Modifier
                .weight(1f)
                .size(112.dp),
            painter = image,
            contentDescription = "App Logo"
        )
        Box(modifier = Modifier
            .weight(3f)) {
            ProgrammeCard(fetchProgrammes, programmeState, setFirstTimeUser, addUserData, addUserDataFailed)
        }
    }
    when(userState) {
        is ActionState.Initial -> {}
        is ActionState.Loading -> {
            Loading()
        }
        is ActionState.Success -> {
            navigateOverview.invoke()
            resetActionState.invoke()
        }
        is ActionState.Error -> {
            addUserDataFailed = true
            Toast.makeText(LocalContext.current, userState.message, Toast.LENGTH_LONG).show()
            resetActionState.invoke()
        }
    }
}

@Composable
fun ProgrammeCard(
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>,
    setFirstTimeUser: () -> Unit,
    addUserData: (String, Programme) -> Unit,
    addUserDataFailed: Boolean
) {
    var studentName by remember { mutableStateOf("") }
    var selectedProgramme by remember { mutableStateOf(Programme("", emptyList())) }

    Card(modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Text(
                text = "Name and Programme",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = studentName,
                    onValueChange = {studentName = it},
                    singleLine = true,
                    label = { Text(text = "Student Name") },
                    trailingIcon = {
                        IconButton(onClick = {
                            studentName = ""
                        }) {
                            if (studentName != "") {
                                Icon(Icons.Filled.Clear, "Clear Student name")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Study Programme",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Choose a study programme from the list. \nThis will help determine the courses show to you.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight
                )
                Spacer(modifier = Modifier.height(12.dp))
                when(programmeState) {
                    is ActionState.Initial -> {}
                    is ActionState.Loading -> {
                        Loading()
                    }
                    is ActionState.Success -> {
                        LazyColumn(
                            modifier = Modifier.padding(bottom = 60.dp)
                        ) {
                            items(programmeState.data) { programme -> ItemCard(programme, selectedProgramme) {
                                selectedProgramme = programme
                            }
                            }
                        }
                    }
                    is ActionState.Error -> {
                        Toast.makeText(LocalContext.current, programmeState.message, Toast.LENGTH_LONG).show()
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Filled.Error, contentDescription = "Error icon", tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Couldn't Load Data", color = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Retry", modifier = Modifier.clickable(onClick = fetchProgrammes), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                        }
                    }
                }
            }

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
                contentAlignment = Alignment.BottomCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (addUserDataFailed) {
                        Text(
                            text = "Something went wrong! Please try again.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                        enabled = selectedProgramme.name != "" && studentName != "",
                        onClick = {
                            setFirstTimeUser.invoke()
                            addUserData(studentName, selectedProgramme)
                        }) {
                        Text(text = "FINISH")
                    }
                }
            }
        }
    }

@Composable
fun ItemCard(programme: Programme, selectedProgramme: Programme, onSelect: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (programme == selectedProgramme),
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary, Color.Gray)
        )
        Text(
            text = programme.name,
            fontSize = 16.sp,
            fontWeight = if (programme == selectedProgramme) FontWeight.Bold else FontWeight.Light ,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProgrammeScreenPreview() {
    ELearningAppTheme {
        ProgrammeScreen({},{},ActionState.Success(programmes),{},{ _, _->},ActionState.Initial,{})
    }
}
