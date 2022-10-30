package com.example.elearningapp.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun ProgrammeScreen() {
    val image: Painter = painterResource(id = R.drawable.e_learning)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Box(modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.7f),
                    painter = image,
                    contentDescription = "App Logo"
                )
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                ProgrammeCard()
            }
        }
    }
}

@Composable
fun ProgrammeCard() {
    var studentName by remember { mutableStateOf("") }
    val programmes = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N")
    var selectedProgramme by remember { mutableStateOf(programmes[0]) }

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
                            text = "Name and Programme",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Student Name",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth(),
                            value = studentName,
                            onValueChange = {studentName = it},
                            singleLine = true,
                            placeholder = { Text(text = "Write the name from your student card.", color = Color.LightGray) }
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
                        LazyColumn(
                            modifier = Modifier.
                            graphicsLayer { alpha = 0.99F }
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
                                    items(programmes) { programme -> ItemCard(programme, selectedProgramme) {
                                        selectedProgramme = programme
                                    }
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter) {
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                            onClick = { /*TODO*/ }) {
                            Text(text = "FINISH")
                        }
                    }
                }

            }
        }
    }

@Composable
fun ItemCard(programme: String, selectedProgramme: String, onSelect: () -> Unit) {
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
            text = programme,
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProgrammeScreen()
        }
    }
}