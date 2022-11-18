package com.example.elearningapp.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.elearningapp.ui.theme.ELearningAppTheme

@Composable
fun Loading() {
    //Box for loading progress indicator
    Box(modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    ELearningAppTheme {
        Loading()
    }
}