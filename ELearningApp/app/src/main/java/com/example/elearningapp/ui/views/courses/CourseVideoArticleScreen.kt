package com.example.elearningapp.ui.views.courses

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.AddNoteButton
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseVideoArticleScreen(
    courseContentState: ActionState<CourseContent?>,
    saveNote: (String, String) -> Unit,
    updateUserCourseSteps: (String, Int) -> Unit
) {
    if (courseContentState is ActionState.Success && courseContentState.data != null) {
        val videoArticleContent = courseContentState.data.videoArticleContent

        val localContext = LocalContext.current
        val configuration = LocalConfiguration.current

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = videoArticleContent.videoThumbNailUri)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
        )

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
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = videoArticleContent.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(((configuration.screenWidthDp - 80) * 9 / 16).dp)
                    .clickable(
                        onClick = {
                            val youTubeIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(videoArticleContent.videoUri)
                            )
                            youTubeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            youTubeIntent.setPackage("com.google.android.youtube")
                            localContext.startActivity(youTubeIntent)
                            updateUserCourseSteps(courseContentState.data.courseName, 2)
                        }
                    ))
                {
                    Image(
                        painter = painter,
                        contentDescription = "Video thumbnail",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, Color.LightGray)
                    )
                    if (painter.state is AsyncImagePainter.State.Success) {
                        Icon(
                            modifier = Modifier.size(80.dp).align(alignment = Alignment.Center),
                            imageVector = Icons.Outlined.PlayCircleFilled,
                            contentDescription = "Account",
                            tint = MaterialTheme.colors.primary
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            androidx.compose.material3.CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                LazyColumn {
                    items(videoArticleContent.bulletPoints) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Canvas(modifier = Modifier
                                .padding(12.dp)
                                .size(6.dp)){
                                drawCircle(Color.Black)
                            }
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
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
                    ActionState.Success(CourseData.allCourseContent[0]),
                    {_,_->},
                    {_,_->}
                )
            }
            }
        }
    }
}