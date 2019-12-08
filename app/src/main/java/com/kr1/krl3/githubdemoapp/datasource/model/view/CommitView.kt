package com.kr1.krl3.githubdemoapp.datasource.model.view

data class CommitView(
    val authorName: String,
    val authorEmail: String,
    val authorDate: String,
    val committerName: String,
    val committerEmail: String,
    val committerDate: String,
    val message: String,
    val comment_count: String
)