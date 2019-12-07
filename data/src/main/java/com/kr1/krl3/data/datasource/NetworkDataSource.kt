package com.kr1.krl3.data.datasource

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.entities.User

interface NetworkDataSource {

    suspend fun getUser(): Either<Failure, User>

    suspend fun getRepos(): Either<Failure, List<Repo>>

    suspend fun getCommits(repoName: String): Either<Failure, List<Commit>>
}