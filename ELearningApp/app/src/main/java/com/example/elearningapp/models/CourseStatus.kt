package com.example.elearningapp.models

data class CourseStatus (
    val courseInformation: CourseInformation = CourseInformation(),
    var stepsCompleted: Int = 0
)