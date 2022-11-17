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
import androidx.compose.ui.res.stringResource
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
    //Image painter
    val image: Painter = painterResource(id = R.drawable.e_learning)

    //MutableState for add user data failed
    var addUserDataFailed by remember { mutableStateOf(false) }

    //Fetches all programmes
    LaunchedEffect(Unit, block = {
        fetchProgrammes.invoke()
    })

    //Content column
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

        //Image logo
        Image(
            modifier = Modifier
                .weight(1f)
                .size(112.dp),
            painter = image,
            contentDescription = stringResource(R.string.app_logo)
        )
        Box(modifier = Modifier
            .weight(3f)) {
            ProgrammeCard(fetchProgrammes, programmeState, setFirstTimeUser, addUserData, addUserDataFailed)
        }
    }
    //When clause for user ActionState
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
    //MutableState for user interaction
    var studentName by remember { mutableStateOf("") }
    var selectedProgramme by remember { mutableStateOf(Programme("", emptyList())) }

    //Content card
    Card(modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(20.dp, 20.dp),
        elevation = 12.dp) {
        //Content column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Text(
                text = stringResource(R.string.name_and_programme),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            //User student name text field
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(),
                    value = studentName,
                    onValueChange = {studentName = it},
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.student_name)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            studentName = ""
                        }) {
                            if (studentName != "") {
                                Icon(Icons.Filled.Clear, stringResource(R.string.clear_student_name))
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.study_programme),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = stringResource(R.string.choose_a_study_programme),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight
                )
                Spacer(modifier = Modifier.height(12.dp))

                //When clause for programme state
                when(programmeState) {
                    is ActionState.Initial -> {}
                    is ActionState.Loading -> {
                        Loading()
                    }
                    //Lazy column for programmes fetched on ActionState Success
                    is ActionState.Success -> {
                        LazyColumn(
                            modifier = Modifier.padding(bottom = 60.dp)
                        ) {
                            items(programmeState.data) { programme -> ProgrammeItem(programme, selectedProgramme) {
                                selectedProgramme = programme
                            }
                            }
                        }
                    }
                    //Icon and message on ActionState Error
                    is ActionState.Error -> {
                        Toast.makeText(LocalContext.current, programmeState.message, Toast.LENGTH_LONG).show()
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(R.string.error_icon), tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.couldnt_load_data), color = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.retry), modifier = Modifier.clickable(onClick = fetchProgrammes), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                        }
                    }
                }
            }
            }
            //Finish button box
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
                contentAlignment = Alignment.BottomCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    //Message if add user data failed
                    if (addUserDataFailed) {
                        Text(
                            text = stringResource(R.string.something_went_wrong),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    //Finish button
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                        enabled = selectedProgramme.name != "" && studentName != "",
                        onClick = {
                            setFirstTimeUser.invoke()
                            addUserData(studentName, selectedProgramme)
                        }) {
                        Text(text = stringResource(R.string.finish_button))
                    }
                }
            }
        }
    }


@Composable
fun ProgrammeItem(programme: Programme, selectedProgramme: Programme, onSelect: () -> Unit) {

    //Row for programme item entry
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
