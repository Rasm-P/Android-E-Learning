package com.example.elearningapp.ui.views.courses

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseSummaryScreen(
    courseContentState: ActionState<CourseContent?>,
    updateUserCourseSteps: (String, Int) -> Unit,
    getUserCourseStepsCompleted: (String) -> Int
){
    //CourseContent ActionState Success conditional
    if (courseContentState is ActionState.Success && courseContentState.data != null) {

        //Course summary values
        val summaryContent = courseContentState.data.courseSummary
        val localContext = LocalContext.current
        val courseName = courseContentState.data.courseName

        //Updates user steps completed
        LaunchedEffect(Unit, block = {
            if (getUserCourseStepsCompleted(courseName) == 4) {
                updateUserCourseSteps(courseName, 5)
            }
        })

        //Summary box content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            //Summary card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                elevation = 12.dp
            ) {
                //Summary text column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = summaryContent.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    //Summary bullet points
                    LazyColumn {
                        items(summaryContent.bulletPoints) {
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
                    Spacer(modifier = Modifier.height(20.dp))

                    //Lean More clickable text
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable(onClick = {
                                val googleIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(summaryContent.learnMoreUri)
                                )
                                googleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                localContext.startActivity(googleIntent)
                            }),
                        text = stringResource(R.string.learn_more),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.error,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
            }
            //Success icon
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = stringResource(R.string.success_icon),
                tint = MaterialTheme.colors.primary.copy(alpha = 0.2f),
                modifier = Modifier.align(alignment = Alignment.Center).size(size = 180.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CourseSummaryScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("course-summary", {}, {}, {}, {}) },
                bottomBar = { CourseBottomNavBar({},{},{},"course-summary") }
            ) {
                    innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                CourseSummaryScreen(
                    ActionState.Success(CourseData.allCourseContent[0]),
                    {_,_->},
                    {4}
                )
            }
            }
        }
    }
}