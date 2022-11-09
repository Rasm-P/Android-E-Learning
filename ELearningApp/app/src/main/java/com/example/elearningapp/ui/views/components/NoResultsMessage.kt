package com.example.elearningapp.ui.views.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun NoResultsMessage(description: String, icon: ImageVector) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(128.dp),
            contentDescription = "No results icon",
            tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
        )
        Text(
            text = description,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Preview
@Composable
fun NoResultsMessagePreview() {
    ELearningAppTheme {
        NoResultsMessage("No courses found",Icons.Filled.SearchOff)
    }
}