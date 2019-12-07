package com.kr1.krl3.githubdemoapp.datasource.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class CommitDB(
    val repoName: String,
    val authorName: String,
    val authorEmail: String,
    val authorDate: String,
    val committerName: String,
    val committerEmail: String,
    val committerDate: String,
    val message: String,
    val comment_count: Int
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null
}