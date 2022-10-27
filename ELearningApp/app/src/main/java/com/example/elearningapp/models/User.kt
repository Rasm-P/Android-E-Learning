package com.example.elearningapp.models

data class User(
    val uid: String = "",
    val username: String = "",
    val studyProgramme: StudyProgramme = StudyProgramme.PROGRAMME1,
    val activeCourses: List<CourseStatus> = emptyList()
)