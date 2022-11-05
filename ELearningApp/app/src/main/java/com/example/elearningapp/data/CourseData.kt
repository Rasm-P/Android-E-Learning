package com.example.elearningapp.data

import com.example.elearningapp.models.Course
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * Pretend data fetched from external API.
 */
object CourseData {
    val trendingCourses: List<Course> = listOf(
        Course(
            "Android Jetpack Compose",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30.minutes,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        ),
        Course(
            "Android Dagger-Hilt",
            "https://miro.medium.com/max/960/1*uEOPfTwyX1EuL-0K8EBBuQ.jpeg",
            "Medium",
            45.minutes,
            5,
            "Dagger-Hilt is part of the Jetpack library that assist in Dependency Injection " +
                    "(DI) framework. Recently I found it is very useful and want to share this useful " +
                    "knowledge to the community.",
            "Programming"
        ),
        Course(
            "Jetpack Compose basics",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30.minutes,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        )
    )

    val allCourses: List<Course> = listOf(
        Course(
            "Android Jetpack Composefffffffffffffffffffffffff",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30.minutes,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        ),
        Course(
            "Android Dagger-Hilt",
            "https://miro.medium.com/max/960/1*uEOPfTwyX1EuL-0K8EBBuQ.jpeg",
            "Medium",
            45.minutes,
            5,
            "Dagger-Hilt is part of the Jetpack library that assist in Dependency Injection " +
                    "(DI) framework. Recently I found it is very useful and want to share this useful " +
                    "knowledge to the community.",
            "Programming"
        ),
        Course(
            "Jetpack Compose basics",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30.minutes,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        )
    )

    fun getCourseByName(name: String): Course? {
        return allCourses.find { course -> course.courseName == name }
    }
}