package com.kr1.krl3.githubdemoapp.datasource.model

import com.kr1.krl3.domain.entities.*
import com.kr1.krl3.githubdemoapp.datasource.model.api.*
import com.kr1.krl3.githubdemoapp.datasource.model.db.CommitDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.RepoDB
import com.kr1.krl3.githubdemoapp.datasource.model.db.UserDB
import com.kr1.krl3.githubdemoapp.datasource.model.view.*

// FROM ENTITY TO VIEW

fun User.toUserView() = UserView(avatar_url, name, company)

fun Repo.toRepoView() = RepoView(id, name, commits_url, open_issues.toString())

fun Committer.toCommitterView() = CommitterView(name, email, date)
fun Author.toAuthorView() = AuthorView(name, email, date)
fun CommitX.toCommitXView() = CommitXView(author.toAuthorView(), committer.toCommitterView(), message, comment_count.toString())
fun Commit.toCommitView() = CommitView(commit.toCommitXView())

// FROM ENTITY TO DB

fun User.toUserDB() = UserDB(avatar_url, name, company)

fun Repo.toRepoDB() = RepoDB(id, name, commits_url, open_issues)

fun Commit.toCommitDB(repoName: String): CommitDB {
    val author = commit.author
    val committer = commit.committer
    return CommitDB(
        repoName,
        author.name,
        author.email,
        author.date,
        committer.name,
        committer.email,
        committer.date,
        commit.message,
        commit.comment_count)
}

// FROM DB TO ENTITY

fun UserDB.toUser() = User(avatar_url, name, company)

fun RepoDB.toRepo() = Repo(id, name, commits_url, open_issues)

fun CommitDB.toCommit(): Commit {

    val commitX = CommitX(
        Author(authorName, authorEmail, authorDate),
        Committer(committerName, committerEmail, committerDate),
        message,
        comment_count)

    return Commit(commitX)
}

// FROM API TO ENTITY

fun UserApi.toUser() = User(avatarUrl, name, company)

fun RepoApi.toRepo() = Repo(id, name, commitsUrl, openIssues)

fun CommitterApi.toCommitter() = Committer(name, email, date)
fun AuthorApi.toAuthor() = Author(name, email, date)
fun CommitXApi.toCommitX() = CommitX(author.toAuthor(), committer.toCommitter(), message, commentCount)
fun CommitApi.toCommit() = Commit(commit.toCommitX())