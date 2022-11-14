package com.example.elearningapp.models

data class CourseStatus (
    val courseInformation: CourseInformation = CourseInformation(),
    var stepsCompleted: Int = 0,
    var courseQuizAnswers: List<Int> = ArrayList()
)