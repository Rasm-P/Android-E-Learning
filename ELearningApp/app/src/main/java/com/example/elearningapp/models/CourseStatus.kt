package com.example.elearningapp.models

data class CourseStatus (
    val courseInformation: CourseInformation = CourseInformation(),
    val stepsCompleted: Int = 0
)