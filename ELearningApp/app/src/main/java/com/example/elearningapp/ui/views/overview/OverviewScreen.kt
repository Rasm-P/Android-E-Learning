package com.example.elearningapp.ui.views.overview

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData.allCourseInformation
import com.example.elearningapp.datasource.CourseData.trendingCourses
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.NoResultsMessage
import com.example.elearningapp.ui.views.components.TopBar
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun OverviewScreen(
    courseInformationState: ActionState<List<CourseInformation>>,
    fetchTrendingCourses: () -> Unit,
    userCoursesStatus: List<CourseStatus>,
    onViewCourse: (CourseInformation) -> Unit
) {
    //Fetches user active courses
    LaunchedEffect(Unit, block = {
        fetchTrendingCourses.invoke()
    })

    //Content column
    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column {
                Text(modifier = Modifier.padding(20.dp,20.dp,20.dp,10.dp),
                    text = stringResource(R.string.new_and_trending),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                //If course information ActionState Success, a lazy row of trending course cards is shown
                if (courseInformationState is ActionState.Success) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        items(courseInformationState.data) { course -> TrendingCourseCard(course, onViewCourse) }
                    }
                //If course information ActionState Error, error icon is shown
                } else if (courseInformationState is ActionState.Error) {
                    Toast.makeText(LocalContext.current, courseInformationState.message, Toast.LENGTH_LONG).show()
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(R.string.error_icon), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = stringResource(R.string.couldnt_load_data), color = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = stringResource(R.string.retry), modifier = Modifier.clickable(onClick = fetchTrendingCourses), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                    }
                }
            }
        }
        //Course progress content box
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .weight(1.2f)) {

            //Course progress column
            Column {
                Text(modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.course_progress),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                //If user course status list is not empty, a lazy column with user course status cards is shown
                if (userCoursesStatus.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)){
                        items(userCoursesStatus) { course -> CourseStatusCard(course, onViewCourse) }
                    }
                //Is user courses status list is empty, a no results message is shown
                } else {
                    NoResultsMessage(stringResource(R.string.course_activity),Icons.Filled.School)
                }
            }
        }
    }
}


@Composable
fun TrendingCourseCard(courseInformation: CourseInformation, onViewCourse: (CourseInformation) -> Unit) {

    //Trending course card content
    Card(modifier = Modifier.width(240.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {

        //Image painter for course image
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = courseInformation.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )

        //Content column for trending course
        Column {
            Box(modifier = Modifier.weight(1f)) {

                //Course image and CircularProgressIndicator
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.course_image),
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
            //Trending course description box
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = courseInformation.courseName,
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
                            text = courseInformation.minutesToComplete.toDuration(DurationUnit.MINUTES).toString() + " - " + courseInformation.steps + stringResource(R.string.steps) ,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    //View Course clickable text
                    Text(
                        modifier = Modifier.clickable( onClick = {onViewCourse(courseInformation)} ),
                        text = stringResource(R.string.view_course),
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
fun CourseStatusCard(courseStatus: CourseStatus, onViewCourse: (CourseInformation) -> Unit) {

    //Course progress value
    val courseProgress = courseStatus.stepsCompleted.toFloat()/courseStatus.courseInformation.steps.toFloat()

    //Course status card
    Card(modifier = Modifier.height(100.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {

        //Image painter for course image
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = courseStatus.courseInformation.imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )

        //Course status content row
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(60.dp)) {

                //Course image and CircularProgressIndicator
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
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            //Course status description column
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f) ,verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = courseStatus.courseInformation.courseName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = courseStatus.courseInformation.minutesToComplete.toDuration(DurationUnit.MINUTES).toString() + " - " + courseStatus.courseInformation.steps + stringResource(R.string.steps),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
                //Course status LinearProgressIndicator
                Column {
                    Text(
                        text = stringResource(R.string.completed) + "${(courseProgress*100).roundToInt()}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                    LinearProgressIndicator(progress = courseProgress)
                }
            }
            //Icon button to view course
            IconButton(
                onClick = {onViewCourse(courseStatus.courseInformation)}
            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = stringResource(R.string.view_course_text), tint = MaterialTheme.colors.primary)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val courses = listOf(CourseStatus(allCourseInformation[0], 3), CourseStatus(allCourseInformation[1], 1), CourseStatus(allCourseInformation[2], 5))
    //val courses = emptyList<CourseStatus>()

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Overview", {}, {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens,
                        {},
                        MenuNavDestination.Overview
                    )
                }
            ) {
                innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                    OverviewScreen(ActionState.Success(trendingCourses), {}, courses,{})
                }
            }
        }
    }
}