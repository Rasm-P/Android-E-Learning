package com.example.elearningapp.models

import kotlin.time.Duration

data class Course (
    val courseName: String,
    val imageUrl: String,
    val difficulty: String,
    val timeToComplete: Duration,
    val steps: Int,
    val description: String,
    val topic: String
)