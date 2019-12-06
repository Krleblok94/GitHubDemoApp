package com.kr1.krl3.domain.usecase

import com.kr1.krl3.domain.Either
import com.kr1.krl3.domain.Failure
import com.kr1.krl3.domain.UseCase
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.repository.CommitsRepository
import com.kr1.krl3.domain.repository.RepoRepository
import com.kr1.krl3.domain.usecase.GetReposAndCommits.GetReposParams

class GetReposAndCommits(private val repoRepository: RepoRepository) :
    UseCase<Map<Repo, List<Commit>>, GetReposParams>() {

    override suspend fun run(params: GetReposParams): Either<Failure, Map<Repo, List<Commit>>> {
        return repoRepository.getRepos(params.username, params.commitsRepository)
    }

    data class GetReposParams(val username: String,
                              val commitsRepository: CommitsRepository)
}