package com.example.elearningapp.models

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class CourseInformation (
    val courseName: String = "",
    val imageUrl: String = "",
    val difficulty: String = "",
    val timeToComplete: Duration = 0.minutes,
    val steps: Int = 0,
    val description: String = "",
    val topic: String = ""
)