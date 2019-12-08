package com.kr1.krl3.domain.entities

data class Commit(
    val authorName: String,
    val authorEmail: String,
    val authorDate: String,
    val committerName: String,
    val committerEmail: String,
    val committerDate: String,
    val message: String,
    val comment_count: Int
)