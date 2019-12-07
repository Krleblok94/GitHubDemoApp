package com.kr1.krl3.domain.entities

data class Commit(
    val commit: CommitX
)

data class CommitX(
    val author: Author,
    val committer: Committer,
    val message: String,
    val comment_count: Int)

data class Author(
    val name: String,
    val email: String,
    val date: String
)

data class Committer(
    val name: String,
    val email: String,
    val date: String
)