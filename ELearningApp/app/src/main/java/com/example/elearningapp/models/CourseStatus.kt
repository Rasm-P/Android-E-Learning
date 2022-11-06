package com.example.elearningapp.models

data class CourseStatus (
    val course: Course = Course(),
    val stepsCompleted: Int = 0
)