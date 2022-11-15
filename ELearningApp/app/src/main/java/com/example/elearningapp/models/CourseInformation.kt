package com.example.elearningapp.models

data class CourseInformation (
    val courseName: String = "",
    val imageUrl: String = "",
    val difficulty: String = "",
    val minutesToComplete: Int = 0,
    val steps: Int = 0,
    val description: String = "",
    val topic: String = ""
)