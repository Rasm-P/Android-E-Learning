package com.example.elearningapp.models

data class User(
    val name: String = "",
    val studyProgramme: StudyProgramme = StudyProgramme.PROGRAMME1,
    val activeCourses: List<CourseStatus> = emptyList()
)