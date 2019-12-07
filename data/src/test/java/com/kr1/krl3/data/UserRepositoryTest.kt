package com.kr1.krl3.data

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.DatabaseFailure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.entities.User
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryTest {

    private val localDataSource = mockk<LocalDataSource>()
    private val networkDataSource = mockk<NetworkDataSource>()
    private val user = mockk<User>()

    private val userRepository =  UserRepositoryImpl(localDataSource, networkDataSource)
    private val username = "Octocat"
    private val errorMessage = "User not found in local data source"

    @BeforeEach
    fun setUp() {
        every { user.name } returns username
        coEvery { localDataSource.saveUser(any()) } just Runs
    }

    @Test
    fun `when API fails, data is not saved locally`() = runBlocking {
        setUpLocalDataSourceResponse(Right(user))
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))

        userRepository.getUser()
        coVerify(exactly = 0) { localDataSource.saveUser(any()) }
    }

    @Test
    fun `when API succeeds, data is saved locally`() = runBlocking {
        setUpLocalDataSourceResponse(Right(user))
        setUpNetworkDataSourceResponse(Right(user))

        userRepository.getUser()
        coVerify(exactly = 1) { localDataSource.saveUser(any()) }
    }

    @Test
    fun `when API succeeds, correct data is returned`() = runBlocking {
        setUpLocalDataSourceResponse(Left(DatabaseFailure("No data in local data source")))
        setUpNetworkDataSourceResponse(Right(user))

        val result = userRepository.getUser()
        assertEquals(username, result.getSuccessData().name)
    }

    @Test
    fun `when API fails, data is returned from local data source`() = runBlocking {
        setUpLocalDataSourceResponse(Right(user))
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))

        val result = userRepository.getUser()
        assertEquals(username, result.getSuccessData().name)
    }

    @Test
    fun `when both API and local fails, failure data is returned`() = runBlocking {
        setUpLocalDataSourceResponse(Left(NetworkFailure(errorMessage)))
        setUpNetworkDataSourceResponse(Left(NetworkFailure("No network")))

        val result = userRepository.getUser()
        assertEquals(errorMessage, result.getFailureData().getMessage())
    }

    private fun setUpLocalDataSourceResponse(response: Either<Failure, User>) {
        coEvery { localDataSource.getUser() } returns response
    }

    private fun setUpNetworkDataSourceResponse(response: Either<Failure, User>) {
        coEvery { networkDataSource.getUser() } returns response
    }
}