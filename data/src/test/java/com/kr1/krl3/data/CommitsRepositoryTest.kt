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
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CommitsRepositoryTest {

    private val localDataSource = mockk<LocalDataSource>()
    private val networkDataSource = mockk<NetworkDataSource>()

    private val apiCommit = mockk<Commit>()
    private val apiCommitMessage = "Api commit message"
    private val apiCommits = listOf(apiCommit)

    private val dbCommit = mockk<Commit>()
    private val dbCommitMessage = "DB commit message"
    private val dbCommits = listOf(dbCommit)

    private val repoName = "Some repo"

    private val commitsRepository = CommitsRepositoryImpl(localDataSource, networkDataSource)

    @BeforeEach
    fun setUp() {
        every { apiCommit.message } returns apiCommitMessage
        every { dbCommit.message } returns dbCommitMessage
        coEvery { localDataSource.saveCommits(any(), any()) } just Runs
    }

    @Test
    fun `when API returns success, API data is returned and data is saved locally`() = runBlocking {
        setUpNetworkDataSourceResponse(Right(apiCommits))
        val result = commitsRepository.getCommits(repoName)

        assertEquals(apiCommitMessage, result.getSuccessData()[0].message)
        coVerify { localDataSource.saveCommits(any(), any()) }
    }

    @Test
    fun `when API returns failure, data is not saved locally`() = runBlocking {
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))
        setUpLocalDataSourceResponse(Right(dbCommits))

        coVerify(exactly = 0) { localDataSource.saveCommits(any(), any()) }
    }

    @Test
    fun `when API returns failure, data is fetched from local source`() = runBlocking {
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))
        setUpLocalDataSourceResponse(Right(dbCommits))

        val result = commitsRepository.getCommits(repoName)

        assertEquals(dbCommitMessage, result.getSuccessData()[0].message)
        coVerify(exactly = 0) { localDataSource.saveCommits(any(), any()) }
    }

    @Test
    fun `when both API and local source fails, failure data is returned with repo name`() = runBlocking {
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))
        setUpLocalDataSourceResponse(Left(DatabaseFailure("No commit data for $repoName")))

        val result = commitsRepository.getCommits(repoName)

        assertTrue(result.isFailure)
        assertEquals("No commit data for $repoName", result.getFailureData().getMessage())
    }

    private fun setUpLocalDataSourceResponse(response: Either<Failure, List<Commit>>) {
        coEvery { localDataSource.getCommits(any()) } returns response
    }

    private fun setUpNetworkDataSourceResponse(response: Either<Failure, List<Commit>>) {
        coEvery { networkDataSource.getCommits(any()) } returns response
    }
}