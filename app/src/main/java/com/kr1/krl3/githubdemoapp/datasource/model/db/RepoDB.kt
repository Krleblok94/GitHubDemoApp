package com.kr1.krl3.githubdemoapp.datasource.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepoDB(
    val id: Int,
    val name: String,
    val commits_url: String,
    val open_issues: Int
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null
}