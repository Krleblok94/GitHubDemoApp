package com.kr1.krl3.githubdemoapp.datasource.model

import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.githubdemoapp.datasource.model.api.CommitApi
import com.kr1.krl3.githubdemoapp.datasource.model.api.RepoApi
import com.kr1.krl3.githubdemoapp.datasource.model.api.UserApi
import com.kr1.krl3.githubdemoapp.datasource.model.db.CommitDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.RepoDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.UserDB
import com.kr1.krl3.githubdemoapp.datasource.model.view.CommitView
import com.kr1.krl3.githubdemoapp.datasource.model.view.RepoView
import com.kr1.krl3.githubdemoapp.datasource.model.view.UserView

// FROM ENTITY TO VIEW

fun User.toUserView() = UserView(avatar_url, name, company)
fun Repo.toRepoView() = RepoView(id, name, commits_url, open_issues.toString())
fun Commit.toCommitView() = CommitView(
    authorName,
    authorEmail,
    authorDate,
    committerName,
    committerEmail,
    committerDate,
    message,
    comment_count.toString()
)

// FROM ENTITY TO DB

fun User.toUserDB() = UserDB(avatar_url, name, company)
fun Repo.toRepoDB() = RepoDB(id, name, commits_url, open_issues)
fun Commit.toCommitDB(repoName: String) = CommitDB(
    repoName,
    authorName,
    authorEmail,
    authorDate,
    committerName,
    committerEmail,
    committerDate,
    message,
    comment_count
)

// FROM DB TO ENTITY

fun UserDB.toUser() = User(avatar_url, name, company)
fun RepoDB.toRepo() = Repo(id, name, commits_url, open_issues)
fun CommitDB.toCommit() = Commit(
    authorName,
    authorEmail,
    authorDate,
    committerName,
    committerEmail,
    committerDate,
    message,
    comment_count
)

// FROM API TO ENTITY

fun UserApi.toUser() = User(avatarUrl, name, company)
fun RepoApi.toRepo() = Repo(id, name, commitsUrl, openIssues)
fun CommitApi.toCommit(): Commit {
    val author = commit.author
    val committer = commit.committer

    return Commit(
        author.name,
        author.email,
        author.date,
        committer.name,
        committer.email,
        committer.date,
        commit.message,
        commit.commentCount
    )
}