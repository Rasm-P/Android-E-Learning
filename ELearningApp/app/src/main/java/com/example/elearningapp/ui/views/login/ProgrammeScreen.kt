package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.Programme
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading

@Composable
fun ProgrammeScreen(
    navigateOverview: () -> Unit,
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>
) {
    val image: Painter = painterResource(id = R.drawable.e_learning)
    fetchProgrammes.invoke()

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
            ProgrammeCard(navigateOverview, fetchProgrammes, programmeState)
        }
    }
}

@Composable
fun ProgrammeCard(
    navigateOverview: () -> Unit,
    fetchProgrammes: () -> Unit,
    programmeState: ActionState<List<Programme>>
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
                when(programmeState) {
                    is ActionState.Initial -> {}
                    is ActionState.Loading -> {
                        Loading()
                    }
                    is ActionState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .graphicsLayer { alpha = 0.99F }
                                .drawWithContent {
                                    val colors = listOf(Color.Transparent, Color.Black)
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(colors),
                                        blendMode = BlendMode.DstOut
                                    )
                                },
                            contentPadding = PaddingValues( vertical = 12.dp)
                        ) {
                            items(programmeState.data) { programme -> ItemCard(programme, selectedProgramme) {
                                selectedProgramme = programme
                            }
                            }
                        }
                    }
                    is ActionState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column {
                                Text(text = "Could not load programmes!", color = MaterialTheme.colors.error)
                                Button(onClick = fetchProgrammes) {
                                    Text(text = "Try again!")
                                }
                            }
                        }
                    }
                }
            }

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
                contentAlignment = Alignment.BottomCenter) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    enabled = selectedProgramme.name != "",
                    onClick = { /*TODO*/ }) {
                    Text(text = "FINISH")
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
        ProgrammeScreen({},{},ActionState.Initial)
    }
}
