package com.example.elearningapp.models

data class CourseContent(
    val courseName: String,
    val articleContent: ArticleContent,
    val videoArticleContent: VideoArticleContent,
    val quizTestQuestions: List<QuizQuestion>,
    val quizResults: QuizResults,
    val courseSummary: CourseSummary
)

data class ArticleContent(
    val title: String,
    val titleText: String,
    val imageUrl: String,
    val subTitle: String,
    val subTitleText: String
)

data class VideoArticleContent(
    val title: String,
    val videoUri: String,
    val videoThumbNailUri: String,
    val bulletPoints: List<String>
)

data class QuizQuestion(
    val title: String,
    val question: String,
    val options: List<String>
)

data class QuizResults(
    val title: String,
    val resultIndices: List<Int>,
    val quizAnswers: List<String>
)

data class CourseSummary(
    val title: String,
    val bulletPoints: List<String>,
    val learnMoreUri: String
)