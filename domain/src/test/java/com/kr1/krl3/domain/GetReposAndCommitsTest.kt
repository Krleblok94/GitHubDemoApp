package com.kr1.krl3.domain

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.UseCase.None
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.repository.RepoRepository
import com.kr1.krl3.domain.usecase.GetReposAndCommits
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetReposAndCommitsTest {

    private val repoRepository = mockk<RepoRepository>()

    private val repo = mockk<Repo>()
    private val commit = mockk<Commit>()
    private val reposAndCommits = mapOf(repo to listOf(commit))

    private val repoName = "dummy repo name"
    private val commitMessage = "dummy commit message"
    private val errorMessage = "Network error"

    private val getReposAndCommits = GetReposAndCommits(repoRepository)

    @BeforeEach
    fun setUp() {
        every { repo.name } returns repoName
        every { commit.message } returns commitMessage
    }

    @Test
    fun `when getRepos succeeds, repo data is in callback`() = runBlocking<Unit> {
        setUpCallback(Either.Right(reposAndCommits))

        getReposAndCommits(None(), this) {
            for (repo in it.getSuccessData().keys) {
                assertEquals(repoName, repo.name)
            }
        }
    }

    @Test
    fun `when getRepos succeeds, commit data is in callback`() = runBlocking<Unit> {
        setUpCallback(Either.Right(reposAndCommits))

        getReposAndCommits(None(), this) {
            for (commit in it.getSuccessData().values) {
                assertEquals(commitMessage, commit[0].message)
            }
        }
    }

    @Test
    fun `when getRepos fails, failure data is in callback`() = runBlocking<Unit> {
        setUpCallback(Either.Left(Failure.NetworkFailure(errorMessage)))

        getReposAndCommits(None(), this) {
            assertEquals(errorMessage, it.getFailureData().getMessage())
        }
    }

    private fun setUpCallback(response: Either<Failure, Map<Repo, List<Commit>>>) {
        coEvery { repoRepository.getRepos() } returns response
    }
}