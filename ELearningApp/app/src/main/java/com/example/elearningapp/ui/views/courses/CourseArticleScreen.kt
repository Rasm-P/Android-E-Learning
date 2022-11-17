package com.example.elearningapp.ui.views.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData.allCourseContent
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.AddNoteButton
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseArticleScreen(
    courseContentState: ActionState<CourseContent?>,
    saveNote: (String, String) -> Unit,
    updateUserCourseSteps: (String, Int) -> Unit
) {
    //ActionState Success conditional
    if (courseContentState is ActionState.Success && courseContentState.data != null) {

        //Course article content
        val articleContent = courseContentState.data.articleContent

        //Article image painter value
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = articleContent.imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
        )

        //Updates user course content step
        LaunchedEffect(Unit, block = {
            updateUserCourseSteps(courseContentState.data.courseName, 1)
        })

        //Article content box
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .verticalScroll(ScrollState(0))) {
            //Article content card
            Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape (5.dp),
            elevation = 12.dp
            ) {
                //Content column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = articleContent.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = articleContent.titleText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    //Image box with CircularProgressIndicator
                    Box(modifier = Modifier.height(200.dp)) {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(R.string.article_image),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
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
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = articleContent.subTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = articleContent.subTitleText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        //Button for AddNoteDialog
        AddNoteButton(saveNote)
    }
}


@Preview(showBackground = true)
@Composable
fun CourseArticleScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("article", {}, {}, {}, {}) },
                bottomBar = { CourseBottomNavBar({},{},{},"article") }
            ) {
                    innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                CourseArticleScreen(
                    ActionState.Success(allCourseContent[0]),
                    {_,_->},
                    {_,_->}
                )
            }
            }
        }
    }
}