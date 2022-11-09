package com.example.elearningapp.ui.views.overview

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData.allCourses
import com.example.elearningapp.datasource.CourseData.trendingCourses
import com.example.elearningapp.models.Course
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.NoResultsMessage
import com.example.elearningapp.ui.views.components.TopBar
import kotlin.math.roundToInt

@Composable
fun OverviewScreen(
    courseState: ActionState<List<Course>>,
    fetchTrendingCourses: () -> Unit,
    userCoursesStatus: List<CourseStatus>
) {
    LaunchedEffect(Unit, block = {
        fetchTrendingCourses.invoke()
    })

    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column {
                Text(modifier = Modifier.padding(20.dp,20.dp,20.dp,10.dp),
                    text = "New and Trending Courses",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                if (courseState is ActionState.Success) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        items(courseState.data) { course -> TrendingCourseCard(course) }
                    }
                } else if (courseState is ActionState.Error) {
                    Toast.makeText(LocalContext.current, courseState.message, Toast.LENGTH_LONG).show()
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Icon(imageVector = Icons.Filled.Error, contentDescription = "Error icon", tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Couldn't Load Data!", color = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Retry", modifier = Modifier.clickable(onClick = fetchTrendingCourses), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                    }
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .weight(1.2f)) {
            Column {
                Text(modifier = Modifier.padding(bottom = 10.dp),
                    text = "Your Course Progress",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                if (userCoursesStatus.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)){
                        items(userCoursesStatus) { course -> CourseStatusCard(course) }
                    }
                } else {
                    NoResultsMessage("No course activity yet",Icons.Filled.School)
                }
            }
        }
    }
}


@Composable
fun TrendingCourseCard(course: Course) {
    Card(modifier = Modifier.width(240.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = course.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )
        Column {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painter,
                    contentDescription = "Course image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                if (painter.state !is AsyncImagePainter.State.Success) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = course.courseName,
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
                    Text(
                        modifier = Modifier.clickable( onClick = {/*TODO*/} ),
                        text = "VIEW COURSE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}


@Composable
fun CourseStatusCard(courseStatus: CourseStatus) {
    val courseProgress = courseStatus.stepsCompleted.toFloat()/courseStatus.course.steps.toFloat()

    Card(modifier = Modifier.height(100.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = courseStatus.course.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(60.dp)) {
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
                        CircularProgressIndicator(
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
                    text = courseStatus.course.courseName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = courseStatus.course.timeToComplete.toString() + " - " + courseStatus.course.steps + " steps",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
                Column {
                    Text(
                        text = "Completed: ${(courseProgress*100).roundToInt()}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                    LinearProgressIndicator(progress = courseProgress)
                }
            }
            IconButton(
                onClick = {/*TODO*/}
            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "View course", tint = MaterialTheme.colors.primary)
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val courses = listOf(CourseStatus(allCourses[0], 3), CourseStatus(allCourses[1], 1), CourseStatus(allCourses[2], 5))
    //val courses = emptyList<CourseStatus>()

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Overview", {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens,
                        {},
                        MenuNavDestination.Overview
                    )
                },
                content = { OverviewScreen(ActionState.Success(trendingCourses), {}, courses) }
            )
        }
        //CourseStatusCard(courses[0])
    }
}