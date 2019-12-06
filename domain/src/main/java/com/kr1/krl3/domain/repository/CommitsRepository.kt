package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.Either
import com.kr1.krl3.domain.Failure
import com.kr1.krl3.domain.entities.Commit
import kotlinx.coroutines.CoroutineDispatcher

interface CommitsRepository {

    suspend fun getCommits(repoName: String): Either<Failure, List<Commit>>
}