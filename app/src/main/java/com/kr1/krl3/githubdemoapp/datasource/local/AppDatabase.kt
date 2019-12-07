package com.kr1.krl3.githubdemoapp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kr1.krl3.githubdemoapp.datasource.model.db.CommitDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.RepoDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.UserDB

@Database(entities = [UserDB::class, RepoDB::class, CommitDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
}