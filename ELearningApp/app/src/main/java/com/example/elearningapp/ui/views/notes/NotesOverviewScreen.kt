package com.example.elearningapp.ui.views.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun NotesOverviewScreen(

) {
    var search by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 6.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp
        ) {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(),
                value = search,
                placeholder = { Text(text = "Search for a note") },
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
        LazyColumn() {

        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun CourseOverviewScreenPreview() {

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Courses", {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens, {}, MenuNavDestination.CourseOverview
                    )
                },
                content = { NotesOverviewScreen() }
            )
        }
    }
}