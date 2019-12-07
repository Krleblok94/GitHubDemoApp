package com.kr1.krl3.githubdemoapp.datasource.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDB(
    val avatar_url: String,
    val name: String,
    val company: String
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null
}