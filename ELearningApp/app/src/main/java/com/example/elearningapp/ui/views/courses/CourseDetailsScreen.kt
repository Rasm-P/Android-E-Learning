package com.example.elearningapp.ui.views.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import com.example.elearningapp.datasource.CourseData.allCourses
import com.example.elearningapp.models.Course
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseDetailsScreen(course: Course) {
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
                    ImageRequest.Builder(LocalContext.current).data(data = course.imageUrl)
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
                            text = course.courseName,
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
                                Text(text = course.topic, color = MaterialTheme.colors.primary)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = course.description,
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
                LazyColumn {

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
fun CourseStepCard() {

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
                    CourseDetailsScreen(allCourses[1])
                }
            }
        }
    }
}