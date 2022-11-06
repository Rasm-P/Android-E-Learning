package com.example.elearningapp.ui.views.courses

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.TopBar
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

@Composable
fun CourseOverviewScreen(programmeTopics: List<String>) {
    var search by remember { mutableStateOf("") }
    var sortTopic by remember { mutableStateOf("Programming") }

    Column(modifier = Modifier
        .fillMaxSize()) {
        Card(modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 6.dp)
            .wrapContentSize(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp) {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(),
                value = search,
                placeholder = { Text(text = "Search for a course") },
                onValueChange = { search = it },
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        search = ""
                    }) {
                        if (search != "") {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                }
            )
        }
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(programmeTopics) { topic ->  TopicButton(topic, sortTopic) { sortTopic = topic } }
        }
        LazyColumn(modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)) {

        }
    }
}

@Composable
fun TopicButton(topic: String, currentTopic: String, onButtonPressed: () -> Unit) {
    val enabled = currentTopic == topic

    Button(
        onClick = onButtonPressed,
        elevation = ButtonDefaults.elevation(disabledElevation = 12.dp, defaultElevation = 12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
            contentColor = if (enabled) MaterialTheme.colors.secondary else Color.LightGray)
    ) {
        Text(text = topic)
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun CourseOverviewScreenPreview() {
    val programmeTopics = listOf("Programming", "Security", "Data Communication", "UX Design", "Big Data" )

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Courses", {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens,
                        {},
                        MenuNavDestination.CourseOverview
                    )
                },
                content = { CourseOverviewScreen(programmeTopics) }
            )
        }
    }
}