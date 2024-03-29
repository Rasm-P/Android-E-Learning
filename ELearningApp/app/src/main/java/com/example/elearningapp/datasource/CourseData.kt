package com.example.elearningapp.datasource

import com.example.elearningapp.models.*

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
                    "It combines a reactive programming model with the ease of use and conciseness " +
                    "of the Kotlin programming language.",
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
            "Jetpack Compose is Android’s recommended modern toolkit for building native UI. " +
                    "It simplifies and accelerates UI development on Android. Quickly bring your app to " +
                    "life with less code, powerful tools, and intuitive Kotlin APIs."
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
                    "quis nostrud exercitation ullamco laboris nisimini",
            "Big Data"
        )
    )

    val allCourseContent: List<CourseContent> = listOf(
        // This course is made using content and text from the following sources:
        // Android Developers, text: https://developer.android.com/jetpack/compose/tutorial
        // Android Developers, text: https://developer.android.com/courses/quizzes/compose/android-11-week-11-2
        // Android Developers, youtube: https://www.youtube.com/watch?v=U5BwfqBpiWU&ab_channel=AndroidDevelopers and https://i.ytimg.com/vi/U5BwfqBpiWU/maxresdefault.jpg
        // Quiz.com, text: https://quizizz.com/admin/quiz/6334064cd8d210001d083fc5/jetpack-compose
        // Golem.de, article image: https://www.golem.de/2107/158523-285566-285565_rc.jpg
        CourseContent(
            "Android Jetpack Compose",
            ArticleContent(
                "Jetpack Compose",
                "Jetpack Compose is a modern toolkit for building native Android UI. Jetpack " +
                        "Compose simplifies and accelerates UI development on Android with less code, " +
                        "powerful tools, and intuitive Kotlin APIs.\n" +
                        "\n" +
                        "In this course, you'll build a simple UI component with declarative functions. " +
                        "You won't be editing any XML layouts or using the Layout Editor. Instead, you will " +
                        "call composable functions to define what elements you want, and the Compose compiler " +
                        "will do the rest.",
                "https://www.golem.de/2107/158523-285566-285565_rc.jpg",
                "Composable functions",
                "Jetpack Compose is built around composable functions. These functions let you " +
                        "define your app's UI programmatically by describing how it should look and " +
                        "providing data dependencies, rather than focusing on the process of the UI's " +
                        "construction (initializing an element, attaching it to a parent, etc.). " +
                        "To create a composable function, just add the @Composable annotation to " +
                        "the function name.\n" +
                        "\n" +
                        "Jetpack Compose uses a Kotlin compiler plugin to transform these composable " +
                        "functions into the app's UI elements. For example, the Text composable function " +
                        "that is defined by the Compose UI library displays a text label on the screen."
            ),
            VideoArticleContent(
                "Jetpack Compose Video",
                "https://www.youtube.com/watch?v=U5BwfqBpiWU&ab_channel=AndroidDevelopers",
                "https://i.ytimg.com/vi/U5BwfqBpiWU/maxresdefault.jpg",
                listOf(
                    "Do more with less code and avoid entire classes of bugs, so code is simple and easy to maintain.",
                    "Just describe your UI, and Compose takes care of the rest. As app state changes, your UI automatically updates.",
                    "Compatible with all your existing code so you can adopt when and where you want. Iterate fast with live previews and full Android Studio support.",
                    "Create beautiful apps with direct access to the Android platform APIs and built-in support for Material Design, Dark theme, animations, and more."
                )
            ),
            listOf(
                QuizQuestion(
                    "Layout in Compose",
                    "Which of the following tell a UI element how to lay out, display, or behave within its parent layout?",
                    listOf(
                        "Modifier parameters",
                        "Composers",
                        "Kotlin functions",
                        "CSS"
                    )
                ),
                QuizQuestion(
                    "Compose Previews",
                    "True or false? To preview composable functions, a developer " +
                            "must deploy the app to an Android device or emulator.",
                    listOf(
                        "True",
                        "False",
                    )
                ),
                QuizQuestion(
                    "Compose Structure",
                    "Which of the following layouts allows you to implement a UI with the basic Material Design layout structure?",
                    listOf(
                        "TopAppBar",
                        "ConstraintLayout",
                        "Scaffold",
                        "Column"
                    )
                ),
                QuizQuestion(
                    "Integrating Compose",
                    "Which of the following are common strategies for integrating " +
                            "Jetpack Compose with an existing Android app?",
                    listOf(
                        "Migrate in bulk with automated tool",
                        "Build a new app",
                        "Use Compose as replacement for whole or part of the existing screens",
                        "Migrate whole project to another language"
                    )
                ),
                QuizQuestion(
                    "Android Emulator",
                    "What is the purpose of using a virtual device, or emulator, in Android Studio?",
                    listOf(
                        "To show a variety of error messages to users",
                        "To experiment with app code safely",
                        "To see what your app looks like in a web browser",
                        "To test your app on a device without having that physical device"
                    )
                )
            ),
            QuizResults(
                "Quiz Answers",
                listOf(1, 2, 3, 3, 4),
                listOf(
                    "Modifier parameters tell a UI element how to lay out, display, or behave within its parent layout.",
                    "Previews lets you preview composable functions within the IDE, without downloading the app to a device or emulator.",
                    "Scaffold allows you to implement a UI with the basic Material Design layout structure.",
                    "You can migrate screens and use Compose as a replacement part of an existing screen or migrate whole fragments to Compose",
                    "The Android Emulator simulates Android devices on your computer so that you can test your app without a real device."
                )
            ),
            CourseSummary(
                "Jetpack Compose",
                listOf(
                    "Jetpack Compose is a modern toolkit for building native Android UI. Jetpack " +
                            "Compose simplifies and accelerates UI development on Android with less code",
                    "Jetpack Compose is built around composable functions. These functions let you " +
                            "define your app's UI programmatically by describing how it should look",
                    "To create a composable function, just add the @Composable annotation to " +
                            "the function name.",
                    "Jetpack Compose uses a Kotlin compiler plugin to transform these composable " +
                            "functions into the app's UI elements.",
                    "Jetpack Compose has direct access to the Android platform APIs and built-in " +
                            "support for Material Design, Dark theme, animations, and more."
                ),
                "https://developer.android.com/jetpack/compose"
            )
        ),
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
            "https://www.youtube.com/watch?v=1Zt6aIqZnqU&ab_channel=AndroidDevelopers",
            "https://i.ytimg.com/vi/mnMCgjuMJPA/maxresdefault.jpg",
            listOf(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna.?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna."
            )
        ),
        listOf(
            QuizQuestion(
                "Lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
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
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
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
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
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
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
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
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua?",
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
            listOf(1, 2, 3, 4, 1),
            listOf(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
            )
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
                        "tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua."
            ),
        "https://developer.android.com/jetpack/compose"
        )
    )
}