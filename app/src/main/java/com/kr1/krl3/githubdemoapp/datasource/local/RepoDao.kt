package com.kr1.krl3.githubdemoapp.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kr1.krl3.githubdemoapp.datasource.model.db.CommitDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.RepoDB

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    fun getRepos(): List<RepoDB>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<RepoDB>)

    @Query("DELETE FROM repo")
    fun deleteRepos()

    @Query("SELECT * FROM commits WHERE repoName = :repoName")
    fun getCommits(repoName: String): List<CommitDB>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommits(commits: List<CommitDB>)

    @Query("DELETE FROM commits WHERE repoName = :repoName")
    fun deleteCommits(repoName: String)
}