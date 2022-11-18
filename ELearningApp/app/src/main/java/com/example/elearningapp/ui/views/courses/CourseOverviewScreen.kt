package com.example.elearningapp.ui.views.courses

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
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData.allCourseInformation
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.ui.views.components.NoResultsMessage
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun CourseOverviewScreen(
    programmeTopics: List<String>,
    coursesState: ActionState<List<CourseInformation>>,
    fetchAllCourses: () -> Unit,
    filterCourses: (String, String) -> List<CourseInformation>,
    onViewCourse: (CourseInformation) -> Unit
) {
    //MutableState for user interaction search and topic filer
    var search by remember { mutableStateOf("") }
    var sortTopic by remember { mutableStateOf("") }

    //Fetches all courses
    LaunchedEffect(Unit, block = {
        fetchAllCourses.invoke()
    })

    //Content column
    Column(modifier = Modifier
        .fillMaxSize()) {
        //Content card
        Card(modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 6.dp)
            .wrapContentSize(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp) {
            //Starch bar
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(),
                value = search,
                placeholder = { Text(text = stringResource(R.string.search_for_a_course)) },
                onValueChange = { search = it },
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search),
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
                                contentDescription = stringResource(R.string.clear_search)
                            )
                        }
                    }
                }
            )
        }
        //Topic buttons lazy row
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(programmeTopics) { topic ->  TopicButton(topic, sortTopic, { sortTopic = topic }) { sortTopic = "" } }
        }
        //Courses ActionState Success conditional
        if (coursesState is ActionState.Success) {
            val courses = filterCourses(search,sortTopic)
            if (courses.isNotEmpty()) {
                //Courses lazy column
                LazyColumn(
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(courses) { course -> CourseCard(course, onViewCourse) }
                }
            } else {
                NoResultsMessage(stringResource(R.string.no_courses_found),Icons.Filled.SearchOff)
            }
            //Course ActionState Error icon
        } else if (coursesState is ActionState.Error) {
            Toast.makeText(LocalContext.current, coursesState.message, Toast.LENGTH_LONG).show()
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(R.string.error_icon), tint = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(R.string.couldnt_load_data), color = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(R.string.retry), modifier = Modifier.clickable(onClick = fetchAllCourses), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
            }
        }
    }
}


@Composable
fun TopicButton(topic: String, currentTopic: String, toggleTopic: () -> Unit, unToggleTopic: () -> Unit) {
    //Topic enabled status value
    val enabled = currentTopic == topic

    //Topic button
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
fun CourseCard(courseInformation: CourseInformation, onViewCourse: (CourseInformation) -> Unit) {

    //Course card
    Card(modifier = Modifier.height(120.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {
        //Image painter
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = courseInformation.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )
        //Content row
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            //Course image box
            Box(modifier = Modifier.width(80.dp)) {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.course_image),
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
            //Course text column
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f) ,verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = courseInformation.courseName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Column {
                    Text(
                        text = stringResource(R.string.difficulty) + courseInformation.difficulty,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = courseInformation.minutesToComplete.toDuration(DurationUnit.MINUTES).toString() + " - " + courseInformation.steps + stringResource(R.string.steps),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(modifier = Modifier.clickable(onClick = {onViewCourse(courseInformation)}), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.view_course),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = stringResource(R.string.view_course), tint = MaterialTheme.colors.primary)
                }
            }
        }
    }
}


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
                topBar = { TopBar("Courses", {}, {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(bottomNavScreens, {}, MenuNavDestination.CourseOverview
                    )
                }
            ) {
                innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                    CourseOverviewScreen(programmeTopics, ActionState.Success(allCourseInformation), {}, { _, _ -> allCourseInformation},{})
                }
            }
        }
    }
}