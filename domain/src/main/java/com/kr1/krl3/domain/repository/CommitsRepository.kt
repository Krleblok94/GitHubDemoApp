package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.Commit

interface CommitsRepository {

    suspend fun getCommits(repoName: String): Either<Failure, List<Commit>>
}