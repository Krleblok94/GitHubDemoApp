package com.kr1.krl3.data

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.DatabaseFailure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.repository.CommitsRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepoRepositoryTest {

    private val localDataSource = mockk<LocalDataSource>()
    private val networkDataSource = mockk<NetworkDataSource>()
    private val commitsRepository = mockk<CommitsRepository>()

    private val repoRepository = RepoRepositoryImpl(localDataSource, networkDataSource, commitsRepository)

    private val repo1 = mockk<Repo>(); private val repo1Name = "Repo 1"
    private val repo2 = mockk<Repo>(); private val repo2Name = "Repo 2"
    private val commit1 = mockk<Commit>(); private val commit1Message = "Commit 1 message"
    private val commit2 = mockk<Commit>(); private val commit2Message = "Commit 2 message"

    private val reposList = listOf(repo1, repo2)
    private val commitsList = listOf(commit1, commit2)

    private val noReposErrorMessage = "Repos data could not be retrieved"

    @BeforeEach
    fun setUp() {
        every { repo1.name } returns repo1Name
        every { repo2.name } returns repo2Name
        every { commit1.commit?.message } returns commit1Message
        every { commit2.commit?.message } returns commit2Message

        coEvery { localDataSource.saveRepos(any()) } just Runs
    }

    @Test
    fun `when both API and local fails, failure is returned`() = runBlocking {
        setUpResponses(
            Left(DatabaseFailure("No data in database")),
            Left(NetworkFailure("No data from network")),
            Left(DatabaseFailure("No commit data")))

        val result = repoRepository.getRepos()
        assertEquals(noReposErrorMessage, result.getFailureData().getMessage())
    }

    @Test
    fun `when API succeeds, repos are saved locally`() = runBlocking {
        setUpResponses(
            Left(DatabaseFailure("No data in database")),
            Right(reposList),
            Left(DatabaseFailure("No commit data")))

        repoRepository.getRepos()

        coVerify(exactly = 1) { localDataSource.saveRepos(any()) }
    }

    @Test
    fun `when API fails, repos are not saved locally`() = runBlocking {
        setUpResponses(
            Right(reposList),
            Left(NetworkFailure("No network")),
            Right(commitsList))

        repoRepository.getRepos()

        coVerify(exactly = 0) { localDataSource.saveRepos(any()) }
    }

    @Test
    fun `when API succeeds for repos and commits, correct data is returned`() = runBlocking {
        setUpResponses(
            Left(DatabaseFailure("No data in database")),
            Right(reposList),
            Right(commitsList))

        val result = repoRepository.getRepos().getSuccessData()
        val repos = result.keys
        val repo1Commits = result[repos.elementAt(0)]

        assertTrue(result.size == 2)
        assertEquals(repos.elementAt(0).name, repo1Name)
        assertEquals(repos.elementAt(1).name, repo2Name)

        assertTrue(repo1Commits?.size == 2)
        assertEquals(repo1Commits?.get(0)?.commit?.message, commit1Message)
        assertEquals(repo1Commits?.get(1)?.commit?.message, commit2Message)
    }

    @Test
    fun `when API fails but local succeeds for repos and commits, correct data is returned`() = runBlocking {
        setUpResponses(
            Right(reposList),
            Left(NetworkFailure("No network")),
            Right(commitsList))

        val result = repoRepository.getRepos().getSuccessData()
        val repos = result.keys
        val repo2Commits = result[repos.elementAt(1)]

        assertTrue(result.size == 2)
        assertEquals(repos.elementAt(0).name, repo1Name)
        assertEquals(repos.elementAt(1).name, repo2Name)

        assertTrue(repo2Commits?.size == 2)
        assertEquals(repo2Commits?.get(0)?.commit?.message, commit1Message)
        assertEquals(repo2Commits?.get(1)?.commit?.message, commit2Message)
    }

    @Test
    fun `when commits fail, empty list is added for the repo`() = runBlocking {
        setUpResponses(
            Right(reposList),
            Right(reposList),
            Left(DatabaseFailure("No data in database")))

        val result = repoRepository.getRepos().getSuccessData()
        val repos = result.keys
        val repo1Commits = result[repos.elementAt(0)]
        val repo2Commits = result[repos.elementAt(1)]

        assertTrue(repo1Commits?.isEmpty() == true)
        assertTrue(repo2Commits?.isEmpty() == true)
    }

    private fun setUpResponses(localResponse: Either<Failure, List<Repo>>,
                               networkResponse: Either<Failure, List<Repo>>,
                               commitsRepositoryResponse: Either<Failure, List<Commit>>
    ) {
        setUpLocalDataSourceRepoResponse(localResponse)
        setUpNetworkDataSourceRepoResponse(networkResponse)
        setUpCommitsRepositoryResponse(commitsRepositoryResponse)
    }

    private fun setUpLocalDataSourceRepoResponse(response: Either<Failure, List<Repo>>) {
        coEvery { localDataSource.getRepos() } returns response
    }

    private fun setUpNetworkDataSourceRepoResponse(response: Either<Failure, List<Repo>>) {
        coEvery { networkDataSource.getRepos() } returns response
    }

    private fun setUpCommitsRepositoryResponse(response: Either<Failure, List<Commit>>) {
        coEvery { commitsRepository.getCommits(any()) } returns response
    }
}