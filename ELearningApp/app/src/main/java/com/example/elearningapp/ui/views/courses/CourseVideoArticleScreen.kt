package com.example.elearningapp.ui.views.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.AddNoteButton
import com.example.elearningapp.ui.views.components.BottomNavBar
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseVideoArticleScreen(
    courseContentState: ActionState<CourseContent?>,
    saveNote: (String, String) -> Unit,
    updateUserCourseSteps: (String, Int) -> Unit
) {
    if (courseContentState is ActionState.Success && courseContentState.data != null) {
        val articleContent = courseContentState.data.videoArticleContent

        LaunchedEffect(Unit, block = {
            updateUserCourseSteps(courseContentState.data.courseName, 2)
        })

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp),
            shape = RoundedCornerShape (5.dp),
            elevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = articleContent.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        AddNoteButton(saveNote)
    }
}


@Preview(showBackground = true)
@Composable
fun CourseVideoArticleScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("video-article", {}, {}, {}, {}) },
                bottomBar = { CourseBottomNavBar({},{},{},"video-article") }
            ) {
                    innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                CourseVideoArticleScreen(
                    ActionState.Success(CourseData.allCourseContent[1]),
                    {_,_->},
                    {_,_->}
                )
            }
            }
        }
    }
}