package com.kr1.krl3.githubdemoapp.datasource.model.view

data class CommitView(
    val commit: CommitXView
)

data class CommitXView(
    val author: AuthorView,
    val committer: CommitterView,
    val message: String,
    val comment_count: String
)

data class AuthorView(
    val name: String,
    val email: String,
    val date: String
)

data class CommitterView(
    val name: String,
    val email: String,
    val date: String
)