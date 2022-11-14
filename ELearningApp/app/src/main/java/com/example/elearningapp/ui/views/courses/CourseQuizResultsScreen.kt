package com.example.elearningapp.ui.views.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.CourseData
import com.example.elearningapp.models.CourseContent
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.AddNoteButton
import com.example.elearningapp.ui.views.components.CourseBottomNavBar
import com.example.elearningapp.ui.views.components.TopBar

@Composable
fun CourseQuizResultsScreen(
    courseContentState: ActionState<CourseContent?>,
    saveNote: (String, String) -> Unit,
    userCourseAnswers: (String) -> List<Int>,
    updateUserCourseSteps: (String, Int) -> Unit
) {

    if (courseContentState is ActionState.Success && courseContentState.data != null) {
        val userCourseQuizAnswers = userCourseAnswers(courseContentState.data.courseName)
        val quizResultContent = courseContentState.data.quizResults

        if (!userCourseQuizAnswers.contains(0)) {
            LaunchedEffect(Unit, block = {
                updateUserCourseSteps(courseContentState.data.courseName, 4)
            })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    elevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = quizResultContent.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(40.dp)) {
                            for (index in quizResultContent.quizAnswers.indices) {
                                val correct = userCourseQuizAnswers[index] == quizResultContent.resultIndices[index]
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Card(
                                        modifier = Modifier
                                            .size(40.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        elevation = 12.dp
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            Text(
                                                modifier = Modifier.align(alignment = Alignment.Center),
                                                text = (index + 1).toString(),
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                    Text(
                                        text = quizResultContent.quizAnswers[index],
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        modifier = Modifier
                                            .padding(start = 12.dp)
                                            .weight(1f)
                                    )
                                    Icon(modifier = Modifier,
                                        imageVector = if (correct) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = "Radio button",
                                        tint = if (correct) Color.Green else Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
            AddNoteButton(saveNote)
        } else {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(
                    imageVector = Icons.Filled.Quiz,
                    modifier = Modifier.size(128.dp),
                    contentDescription = "Quiz not answered yet",
                    tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
                Text(
                    text = "Quiz has not been answered yet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CourseQuizResultsScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("quiz-results", {}, {}, {}, {}) },
                bottomBar = { CourseBottomNavBar({},{},{},"quiz-results") }
            ) {
                    innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                CourseQuizResultsScreen(
                    ActionState.Success(CourseData.allCourseContent[0]),
                    {_,_->},
                    { listOf(1, 5, 3, 1, 2) },
                    {_,_->}
                )
            }
            }
        }
    }
}