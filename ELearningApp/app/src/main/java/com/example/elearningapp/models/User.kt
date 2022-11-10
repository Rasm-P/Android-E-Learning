package com.example.elearningapp.models

data class User(
    val name: String = "",
    val studyProgramme: Programme = Programme(),
    var activeCourses: ArrayList<CourseStatus> = ArrayList()
)