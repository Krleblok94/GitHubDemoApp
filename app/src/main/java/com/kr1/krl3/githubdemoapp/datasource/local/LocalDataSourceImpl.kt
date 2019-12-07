package com.kr1.krl3.githubdemoapp.datasource.local

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.DatabaseFailure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.githubdemoapp.datasource.model.*

class LocalDataSourceImpl(private val appDatabase: AppDatabase) : LocalDataSource {

    override suspend fun getUser(): Either<Failure, User> {
        val user = appDatabase.userDao().getUser()

        return if (user == null) Left(DatabaseFailure("No user data")) else Right(user.toUser())
    }

    override suspend fun saveUser(user: User) {
        val userDao = appDatabase.userDao()

        appDatabase.runInTransaction {
            userDao.deleteUser()
            userDao.insertUser(user.toUserDB())
        }
    }

    override suspend fun getRepos(): Either<Failure, List<Repo>> {
        val repos = appDatabase.repoDao().getRepos()

        return if (repos == null || repos.isEmpty()) Left(DatabaseFailure("No repo data")) else
            Right(repos.map { it.toRepo() })
    }

    override suspend fun saveRepos(repos: List<Repo>) {
        val repoDao = appDatabase.repoDao()

        appDatabase.runInTransaction {
            repoDao.deleteRepos()
            repoDao.insertRepos(repos.map { it.toRepoDB() })
        }
    }

    override suspend fun getCommits(repoName: String): Either<Failure, List<Commit>> {
        val commits = appDatabase.repoDao().getCommits(repoName)

        return if (commits == null || commits.isEmpty())
            Left(DatabaseFailure("No commits for repo $repoName")) else Right(commits.map { it.toCommit() })
    }

    override suspend fun saveCommits(repoName: String, commits: List<Commit>) {
        val repoDao = appDatabase.repoDao()

        appDatabase.runInTransaction {
            repoDao.deleteCommits(repoName)
            repoDao.insertCommits(commits.map { it.toCommitDB(repoName) })
        }
    }
}