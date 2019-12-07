package com.kr1.krl3.domain.usecase

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.UseCase
import com.kr1.krl3.domain.common.UseCase.None
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.repository.RepoRepository

class GetReposAndCommits(private val repoRepository: RepoRepository) : UseCase<Map<Repo, List<Commit>>, None>() {

    override suspend fun run(params: None): Either<Failure, Map<Repo, List<Commit>>> = repoRepository.getRepos()
}