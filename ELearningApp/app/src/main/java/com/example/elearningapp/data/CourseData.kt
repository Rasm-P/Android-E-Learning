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
            "Jetpack Compose basics",
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
            "Jetpack Compose basics",
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
}