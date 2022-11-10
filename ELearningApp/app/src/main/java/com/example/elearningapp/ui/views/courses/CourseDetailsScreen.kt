package com.example.elearningapp.ui.views.courses

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData.allCourseContent
import com.example.elearningapp.datasource.CourseData.allCourseInformation
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.models.CourseInformation
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.Loading
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseDetailsScreen(
    courseInformation: CourseInformation,
    fetchCourseContent: () -> Unit,
    courseContentState: ActionState<CourseContent?>,
    stepStatus: Int
) {
    LaunchedEffect(Unit, block = {
        fetchCourseContent.invoke()
    })

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                elevation = 12.dp
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = courseInformation.imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                )
                Column {
                    Box(modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Course image",
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
                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(
                                        text = "Difficulty: " + courseInformation.difficulty,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = courseInformation.timeToComplete.toString() + " - " + courseInformation.steps + " steps",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                                Text(text = courseInformation.topic, color = MaterialTheme.colors.primary)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Description",
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
                                    .clickable(onClick = {/*TODO*/ }),
                                text = "Read More",
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
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .weight(1f)) {
            Column {
                Text(modifier = Modifier.padding(bottom = 10.dp),
                    text = "Course Steps",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                when(courseContentState) {
                    is ActionState.Initial -> {}
                    is ActionState.Loading -> {
                        Loading()
                    }
                    is ActionState.Success -> {
                        val courseContent = courseContentState.data
                        if (courseContent != null) {
                            Column(
                                modifier = Modifier.padding(bottom = 60.dp)
                            ) {
                                CourseStepCard(0 <= stepStatus, courseContent.articleContent.title)
                                CourseStepCard(1 <= stepStatus, courseContent.videoArticleContent.title)
                                CourseStepCard(2 <= stepStatus, "Quiz Test")
                                CourseStepCard(3 <= stepStatus, courseContent.quizResults.title)
                                CourseStepCard(4 == stepStatus, courseContent.courseSummary.title)
                            }
                        } else {
                            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(imageVector = Icons.Filled.Error, contentDescription = "Error icon", tint = Color.LightGray)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = "No steps found for this course", color = Color.LightGray)
                            }
                        }
                    }
                    is ActionState.Error -> {
                        Toast.makeText(LocalContext.current, courseContentState.message, Toast.LENGTH_LONG).show()
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Filled.Error, contentDescription = "Error icon", tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Couldn't Load Data", color = Color.LightGray)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Retry", modifier = Modifier.clickable(onClick = fetchCourseContent), color = MaterialTheme.colors.error, textDecoration = TextDecoration.Underline)
                        }
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(alignment = Alignment.BottomCenter),
            onClick = { /*TODO*/ }) {
            Text(text = "START COURSE")
        }
    }
}


@Composable
fun CourseStepCard(completedStep: Boolean, title: String) {
    Text(text = completedStep.toString() + title)
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
                topBar = { TopBar("course-details", {}, {}, {}) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    CourseDetailsScreen(allCourseInformation[0],{},ActionState.Success(allCourseContent[0]),3)
                }
            }
        }
    }
}