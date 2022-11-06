package com.example.elearningapp.models

data class User(
    val name: String = "",
    val studyProgramme: Programme = Programme(),
    val activeCourses: List<CourseStatus> = emptyList()
)