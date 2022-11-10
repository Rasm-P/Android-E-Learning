package com.example.elearningapp.datasource

import com.example.elearningapp.models.*
import kotlin.time.Duration.Companion.minutes

/**
 * Pretend data fetched from external API.
 */
object CourseData {
    val trendingCourses: List<CourseInformation> = listOf(
        CourseInformation(
            "Android Jetpack Compose",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        ),
        CourseInformation(
            "Android Dagger-Hilt",
            "https://miro.medium.com/max/960/1*uEOPfTwyX1EuL-0K8EBBuQ.jpeg",
            "Medium",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Programming"
        ),
        CourseInformation(
            "SSH Encryption",
            "https://visualmodo.com/wp-content/uploads/2022/06/What-Is-SSH-Understanding-Secure-Socket-Shell-Encryption-Ports-and-Connection.png",
            "Hard",
            55,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Security"
        ),
        CourseInformation(
            "Transport Protocols",
            "https://dkrn4sk0rn31v.cloudfront.net/uploads/2019/06/11140050/sockets-redes-tcp-udp.png",
            "Hard",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Data Communication"
        )
    )

    val allCourseInformation: List<CourseInformation> = listOf(
        CourseInformation(
            "Android Jetpack Compose",
            "https://miro.medium.com/max/715/1*XHnWaOao9drSl25Cfhz6WA.png",
            "Easy",
            30,
            5,
            "Jetpack Compose is a modern toolkit designed to simplify UI development. " +
                    "It combines a reactive programming model with the conciseness and ease of " +
                    "use of the Kotlin programming language.",
            "Programming"
        ),
        CourseInformation(
            "Android Dagger-Hilt",
            "https://miro.medium.com/max/960/1*uEOPfTwyX1EuL-0K8EBBuQ.jpeg",
            "Medium",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Programming"
        ),
        CourseInformation(
            "SSH Encryption",
            "https://visualmodo.com/wp-content/uploads/2022/06/What-Is-SSH-Understanding-Secure-Socket-Shell-Encryption-Ports-and-Connection.png",
            "Hard",
            55,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Security"
        ),
        CourseInformation(
            "Transport Protocols",
            "https://dkrn4sk0rn31v.cloudfront.net/uploads/2019/06/11140050/sockets-redes-tcp-udp.png",
            "Hard",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Data Communication"
        ),
        CourseInformation(
            "Wireframe Prototypes",
            "https://landing.moqups.com/blog/wp-content/uploads/2022/07/header-blog2-1200x0-c-default.png",
            "Easy",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "UX Design"
        ),
        CourseInformation(
            "Working with Big Data",
            "https://miro.medium.com/max/800/1*cDO5wuA0NdevLb45zHRvog.jpeg",
            "Hard",
            45,
            5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Big Data"
        )
    )

    val allCourseContent: List<CourseContent> = listOf(
        dummyCourse("Android Jetpack Compose"),
        dummyCourse("Android Dagger-Hilt"),
        dummyCourse("SSH Encryption"),
        dummyCourse("Transport Protocols"),
        dummyCourse("Wireframe Prototypes"),
        dummyCourse("Working with Big Data"),
    )

    fun getCourseContentByName(name: String): CourseContent? {
        return allCourseContent.find { course -> course.courseName == name }
    }
}

private fun dummyCourse(title: String): CourseContent {
    return CourseContent(
        title,
        ArticleContent(
            "Lorem ipsum",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
                    "do eiusmod tempor incididunt ut labore et dolore magna aliqua. \n" +
                    "\n" +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                    "laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
                    "irure dolor in reprehenderit in voluptate velit esse cillum " +
                    "dolore eu fugiat nulla pariatur",
            "https://miro.medium.com/max/720/1*3cUYx18pBvMzL4cv6ejGdg.png",
            "Lorem ipsum",
            "At vero eos et accusamus et iusto odio dignissimos ducimus qui " +
                    "blanditiis praesentium voluptatum deleniti atque corrupti quos " +
                    "dolores et quas molestias excepturi sint occaecati cupiditate non " +
                    "provident, similique sunt in culpa qui officia deserunt mollitia " +
                    "animi, id est laborum et dolorum fuga.\n" +
                    "\n" +
                    "Nam libero tempore, cum soluta nobis est eligendi optio cumque " +
                    "nihil impedit quo minus id quod maxime placeat facere possimus, " +
                    "omnis voluptas assumenda est, omnis dolor repellendus."
        ),
        VideoArticleContent(
            "Lorem ipsum",
            "https://youtu.be/1Zt6aIqZnqU",
            listOf(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?"
            )
        ),
        listOf(
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet?",
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                )
            ),
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet?",
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                )
            ),
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet?",
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                )
            ),
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet?",
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                )
            ),
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet?",
                listOf(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                )
            )
        ),
        QuizResults(
            "Lorem ipsum",
            listOf(1, 3, 2, 0, 1)
        ),
        CourseSummary(
            "Lorem ipsum",
            listOf(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua."
            )
        )
    )
}