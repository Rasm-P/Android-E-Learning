package com.example.elearningapp.ui.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elearningapp.R
import com.example.elearningapp.navigation.CourseDestination
import com.example.elearningapp.ui.theme.ELearningAppTheme

private val NavBarHeight = 60.dp

@Composable
fun CourseBottomNavBar(
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onFinishedPressed: () -> Unit,
    currentRoute: String
) {
    //NavBar surface
    Surface(
        Modifier
            .fillMaxWidth()
            .height(NavBarHeight), color = MaterialTheme.colors.primary
    ) {
        //Navbar row content
        Row(Modifier.padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            //Previous arrow
            Row(modifier = Modifier.clickable(onClick = onPreviousPressed), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.previous), tint = MaterialTheme.colors.secondary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(R.string.previous),
                    fontWeight = FontWeight.Medium)
            }
            //Conditional for Next or Finish arrow
            if (currentRoute == CourseDestination.CourseSummary.route) {
                Row(
                    modifier = Modifier.clickable(onClick = onFinishedPressed),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.finish),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            } else if (currentRoute != CourseDestination.CourseQuiz.route) {
                Row(
                    modifier = Modifier.clickable(onClick = onNextPressed),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.next),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseBottomNavBarPreview() {
    ELearningAppTheme {
        CourseBottomNavBar({}, {}, {}, "course-summary")
    }
}