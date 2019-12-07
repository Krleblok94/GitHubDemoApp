package com.kr1.krl3.data

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.NoReposFailure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.repository.CommitsRepository
import com.kr1.krl3.domain.repository.RepoRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RepoRepositoryImpl(private val localDataSource: LocalDataSource,
                         private val networkDataSource: NetworkDataSource,
                         private val commitsRepository: CommitsRepository) : RepoRepository {

    private val reposAndCommits = mutableMapOf<Repo, List<Commit>>()

    override suspend fun getRepos(): Either<Failure, Map<Repo, List<Commit>>> {

        val apiRepos = networkDataSource.getRepos()

        when (apiRepos.isSuccess) {
            true -> handleApiSuccess(apiRepos)
            false -> getReposFromLocal()
        }

        return if (reposAndCommits.isEmpty())
            Left(NoReposFailure("Repos data could not be retrieved")) else Right(reposAndCommits)
    }

    private suspend fun handleApiSuccess(apiRepos: Either<Failure, List<Repo>>) {
        localDataSource.saveRepos(apiRepos.getSuccessData())
        getCommitsForRepos(apiRepos)
    }

    private suspend fun getReposFromLocal() {
        val dbRepos = localDataSource.getRepos()

        when (dbRepos.isSuccess) {
            true -> getCommitsForRepos(dbRepos)
            false -> return
        }
    }

    private suspend fun getCommitsForRepos(reposData: Either<Failure, List<Repo>>) = coroutineScope {
        val repos = reposData.getSuccessData()

        for (repo in repos) {
            launch { getCommitsAsynchronously(repo) }
        }
    }

    private suspend fun getCommitsAsynchronously(repo: Repo) {
        val commitsResult = commitsRepository.getCommits(repo.name)

        when (commitsResult.isSuccess) {
            true -> reposAndCommits[repo] = commitsResult.getSuccessData()
            false -> reposAndCommits[repo] = emptyList()
        }
    }
}