package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo

interface RepoRepository {

    suspend fun getRepos(): Either<Failure, Map<Repo, List<Commit>>>
}