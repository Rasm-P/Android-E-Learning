package com.example.elearningapp.ui.views.courses

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.data.CourseData.allCourses
import com.example.elearningapp.models.Course

@Composable
fun CourseOverviewScreen(
    programmeTopics: List<String>,
    coursesState: ActionState<List<Course>>,
    fetchAllCourses: () -> Unit,
    filterCourses: (String, String) -> List<Course>
) {
    var search by remember { mutableStateOf("") }
    var sortTopic by remember { mutableStateOf("") }

    LaunchedEffect(Unit, block = {
        fetchAllCourses.invoke()
    })

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
            items(programmeTopics) { topic ->  TopicButton(topic, sortTopic, { sortTopic = topic }) { sortTopic = "" } }
        }
        if (coursesState is ActionState.Success) {
            LazyColumn(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(filterCourses(search,sortTopic)) { course -> CourseCard(course) }
            }
        } else if (coursesState is ActionState.Error) {
            Toast.makeText(LocalContext.current, coursesState.message, Toast.LENGTH_LONG).show()
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Filled.Error, contentDescription = "Error icon", tint = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Couldn't Load Data!", color = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Retry", modifier = Modifier.clickable(onClick = fetchAllCourses), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
            }
        }
    }
}


@Composable
fun TopicButton(topic: String, currentTopic: String, toggleTopic: () -> Unit, unToggleTopic: () -> Unit) {
    val enabled = currentTopic == topic

    Button(
        onClick = if (!enabled) toggleTopic else unToggleTopic,
        elevation = ButtonDefaults.elevation(disabledElevation = 12.dp, defaultElevation = 12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
            contentColor = if (enabled) MaterialTheme.colors.secondary else Color.LightGray)
    ) {
        Text(text = topic)
    }
}


@Composable
fun CourseCard(course: Course) {
    Card(modifier = Modifier.height(120.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = course.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(80.dp)) {
                Image(
                    painter = painter,
                    contentDescription = "Course image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
                )
                if (painter.state !is AsyncImagePainter.State.Success) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        androidx.compose.material3.CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f) ,verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = course.courseName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Column {
                    Text(
                        text = "Difficulty: " + course.difficulty,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = course.timeToComplete.toString() + " - " + course.steps + " steps",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(modifier = Modifier.clickable(onClick = {/*TODO*/}), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "VIEW COURSE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "To courses", tint = MaterialTheme.colors.primary)
                }
            }
        }
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
                    BottomNavBar(bottomNavScreens, {}, MenuNavDestination.CourseOverview
                    )
                },
                content = { CourseOverviewScreen(programmeTopics, ActionState.Success(allCourses), {}, {_ ,_ -> allCourses}) }
            )
        }
    }
}