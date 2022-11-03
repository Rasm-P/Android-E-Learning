package com.example.elearningapp.ui.views.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.data.CourseData
import com.example.elearningapp.models.Course
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.login.ItemCard

@Composable
fun OverviewScreen(courseState: ActionState<List<Course>>) {
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
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .weight(1.5f)) {
            Column {
                Text(modifier = Modifier.padding(bottom = 10.dp),
                    text = "Your Course Progress",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                LazyColumn() {

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
                //Temporary animation while loading poster image
                if (painter.state !is AsyncImagePainter.State.Success) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = colors.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            Box(modifier = Modifier.weight(1f)) {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            OverviewScreen(ActionState.Success(CourseData.trendingCourses))
        }
    }
}