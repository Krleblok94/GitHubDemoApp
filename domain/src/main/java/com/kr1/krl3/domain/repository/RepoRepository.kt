package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.Either
import com.kr1.krl3.domain.Failure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import kotlinx.coroutines.CoroutineDispatcher

interface RepoRepository {

    suspend fun getRepos(username: String, commitsRepository: CommitsRepository)
            : Either<Failure, Map<Repo, List<Commit>>>
}