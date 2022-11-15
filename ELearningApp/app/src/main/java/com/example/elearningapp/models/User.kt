package com.example.elearningapp.models

data class User(
    var name: String = "",
    var studyProgramme: Programme = Programme(),
    var activeCourses: ArrayList<CourseStatus> = ArrayList()
)