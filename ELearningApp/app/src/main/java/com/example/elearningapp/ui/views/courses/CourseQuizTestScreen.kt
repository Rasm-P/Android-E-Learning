package com.example.elearningapp.ui.views.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun CourseQuizTestScreen(
    courseContentState: ActionState<CourseContent?>,
    userCourseAnswers: (String) -> List<Int>,
    updateUserCourseSteps: (String, Int) -> Unit,
    updateUserCourseQuizAnswers: (String, List<Int>) -> Unit
) {
    //CourseContent ActionState Success conditional
    if (courseContentState is ActionState.Success && courseContentState.data != null) {

        //Quiz questions
        val quizTestQuestions = courseContentState.data.quizTestQuestions

        //Users quiz answers
        val userCourseQuizAnswers = userCourseAnswers(courseContentState.data.courseName)
        val quizAnswerList = userCourseQuizAnswers.ifEmpty { List(quizTestQuestions.size) { 0 } }

        //MutableState for current quiz index and answers
        var currentQuestionIndex by remember { mutableStateOf(0) }
        var questionAnswers by remember { mutableStateOf(quizAnswerList) }

        //Content column
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            //Quiz question indicators
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                for (index in quizTestQuestions.indices) {
                    Card(modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = { currentQuestionIndex = index }),
                        shape = RoundedCornerShape (5.dp),
                        elevation = 12.dp,
                        backgroundColor = if (index == currentQuestionIndex) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(modifier = Modifier.align(alignment = Alignment.Center),
                                text = (index+1).toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (index == currentQuestionIndex) MaterialTheme.colors.secondary else Color.Black
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            //Quiz question card
            Card(shape = RoundedCornerShape (5.dp),
                elevation = 12.dp
            ) {
                //Quiz content column
                Column(modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)) {
                    Text(
                        text = quizTestQuestions[currentQuestionIndex].title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = quizTestQuestions[currentQuestionIndex].titleText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = quizTestQuestions[currentQuestionIndex].question,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    //Quiz questions
                    for ((index, option) in quizTestQuestions[currentQuestionIndex].options.withIndex()) {
                        val answer = questionAnswers.toMutableList()
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (answer[currentQuestionIndex] == index+1),
                                onClick = {answer[currentQuestionIndex] = index+1
                                    questionAnswers = answer},
                                colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary, Color.Gray)
                            )
                            Text(
                                text = option,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            //Row for Previous and Next buttons
            Row(modifier = Modifier.fillMaxWidth() ,horizontalArrangement = Arrangement.SpaceBetween) {
                val previousEnabled = currentQuestionIndex > 0
                val nextEnabled = currentQuestionIndex < quizTestQuestions.size-1

                Button(modifier = Modifier.width(130.dp),
                    onClick = { if (previousEnabled) {currentQuestionIndex -= 1} },
                    elevation = ButtonDefaults.elevation(disabledElevation = 12.dp, defaultElevation = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = if (previousEnabled) MaterialTheme.colors.primary else Color.LightGray)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.previous)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.previous),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Button(modifier = Modifier.width(130.dp),
                    onClick = { if (nextEnabled) {currentQuestionIndex += 1} },
                    elevation = ButtonDefaults.elevation(disabledElevation = 12.dp, defaultElevation = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = if (nextEnabled) MaterialTheme.colors.primary else Color.LightGray)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = stringResource(R.string.next)
                        )
                    }
                }
            }
        }

        //Box for Answer button
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                enabled = !questionAnswers.contains(0),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(alignment = Alignment.BottomCenter),
                onClick = { updateUserCourseQuizAnswers(courseContentState.data.courseName, questionAnswers)
                    updateUserCourseSteps(courseContentState.data.courseName, 3)}) {
                Text(text = stringResource(R.string.answer))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CourseQuizTestScreenPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("quiz-test", {}, {}, {}, {}) },
                bottomBar = { CourseBottomNavBar({},{},{},"quiz-test") }
            ) {
                    innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                CourseQuizTestScreen(
                    ActionState.Success(CourseData.allCourseContent[0]),
                    {emptyList()},
                    {_,_->},
                    {_,_->}
                )
            }
            }
        }
    }
}