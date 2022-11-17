package com.example.elearningapp.ui.views.courses

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.elearningapp.datasource.CourseData.allCourseContent
import com.example.elearningapp.datasource.CourseData.allCourseInformation
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading
import com.example.elearningapp.ui.views.components.TopBar
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun CourseDetailsScreen(
    courseInformation: CourseInformation,
    fetchCourseContent: () -> Unit,
    courseContentState: ActionState<CourseContent?>,
    stepStatus: Int,
    hasUserStartedCourse: (String) -> Boolean,
    updateUserActiveCourses: (CourseInformation) -> Unit,
    navigateToScreenStep: (Int) -> Unit
) {
    //MutableState for ReadMoreDialog
    var showReadMoreDialog by remember { mutableStateOf(false) }

    //Image painter for course image
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = courseInformation.imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )

    //Fetches course content
    LaunchedEffect(Unit, block = {
        fetchCourseContent.invoke()
    })

    //Content column
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            //Course description card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                elevation = 12.dp
            ) {
                Column {
                    Box(modifier = Modifier.weight(1f)
                    ) {
                        //Course image
                        Image(
                            painter = painter,
                            contentDescription = stringResource(R.string.course_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                drawRect(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.5f)
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height * 2)
                                    ),
                                    size = size
                                )
                            })
                        //CircularProgressIndicator for when loading image
                        if (painter.state !is AsyncImagePainter.State.Success) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        Text(modifier = Modifier
                            .align(alignment = Alignment.BottomStart)
                            .padding(10.dp),
                            text = courseInformation.courseName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.secondary
                        )

                    }
                    //Course description
                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.difficulty) + courseInformation.difficulty,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = courseInformation.minutesToComplete.toDuration(
                                            DurationUnit.MINUTES).toString() + " - " + courseInformation.steps + stringResource(R.string.steps),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                                Text(text = courseInformation.topic, color = MaterialTheme.colors.primary)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(R.string.description),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = courseInformation.description,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                modifier = Modifier
                                    .align(alignment = Alignment.End)
                                    .clickable(onClick = { showReadMoreDialog = true }),
                                text = stringResource(R.string.read_more),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colors.error,
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                            )
                        }
                    }
                }
            }
        }
        //Course steps box
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .weight(1f)) {
            //Content column
            Column {
                Text(modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.course_steps),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                //When clause for courseSteps ActionState
                when(courseContentState) {
                    is ActionState.Initial -> {}
                    is ActionState.Loading -> {
                        Loading()
                    }
                    //Show course steps when ActionState success
                    is ActionState.Success -> {
                        val courseContent = courseContentState.data
                        if (courseContent != null) {
                            Column(
                                modifier = Modifier
                                    .padding(bottom = 60.dp)
                                    .verticalScroll(
                                        ScrollState(0)
                                    )
                            ) {
                                //Course steps
                                CourseStepCard(1, stepStatus, stringResource(R.string.article), navigateToScreenStep)
                                CourseStepCard(2, stepStatus, stringResource(R.string.video_article), navigateToScreenStep)
                                CourseStepCard(3, stepStatus, stringResource(R.string.quiz_test), navigateToScreenStep)
                                CourseStepCard(4, stepStatus, stringResource(R.string.quiz_results), navigateToScreenStep)
                                CourseStepCard(5, stepStatus, stringResource(R.string.course_summary), navigateToScreenStep)
                            }
                        } else {
                            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(R.string.error_icon), tint = Color.LightGray)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = stringResource(R.string.no_steps_found), color = Color.LightGray)
                            }
                        }
                    }
                    //ActionState Error if course steps cannot be loaded
                    is ActionState.Error -> {
                        Toast.makeText(LocalContext.current, courseContentState.message, Toast.LENGTH_LONG).show()
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(R.string.error_icon), tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.couldnt_load_data), color = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.retry), modifier = Modifier.clickable(onClick = fetchCourseContent), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                        }
                    }
                }
            }
        }
    }
    //Bottom button box
    Box(modifier = Modifier.fillMaxSize()) {
        if (!hasUserStartedCourse(courseInformation.courseName)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomCenter),
                onClick = { updateUserActiveCourses(courseInformation) }) {
                Text(text = stringResource(R.string.start_course))
            }
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomCenter),
                onClick = { navigateToScreenStep(stepStatus) }) {
                Text(text = stringResource(R.string.continue_course))
            }
        }
    }

    //ReadMoreDialog conditional
    if (showReadMoreDialog) {
        ReadMoreDialog({ showReadMoreDialog = false }, courseInformation)
    }
}


@Composable
fun CourseStepCard(step: Int, stepStatus: Int, title: String, navigateToScreenStep: (Int) -> Unit) {
    //Steps completed value
    val completedStep = step <= stepStatus

    //Course step card
    Card(modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp
    ) {
        //Content row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Icon step indicator
            Icon(modifier = Modifier,
                imageVector = if (completedStep) Icons.Outlined.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = stringResource(R.string.radio_button),
                tint = if (completedStep) Color.Green else Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(modifier = Modifier.weight(1f),
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            //Jump to section if step has been completed
            if (completedStep) {
                Icon(
                    modifier = Modifier.clickable(onClick = { navigateToScreenStep(step) }),
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.jump_to_section),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}


@Composable
fun ReadMoreDialog(onDismiss: () -> Unit, courseInformation: CourseInformation) {
    //Read more dialog
    AlertDialog(
        title = {
            Text(
                text = courseInformation.courseName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Text(
                text = courseInformation.description,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Light
            )
        },
        onDismissRequest = onDismiss,
        buttons = {
            //Back button content box
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    modifier = Modifier.padding(bottom = 15.dp),
                    onClick = onDismiss) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colors.secondary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = stringResource(R.string.back))
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CourseDetailsScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("course-details", {}, {}, {}, {}) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    CourseDetailsScreen(allCourseInformation[0],{},ActionState.Success(allCourseContent[0]),3, {false}, {}, {})
                }
            }
        }
    }
}