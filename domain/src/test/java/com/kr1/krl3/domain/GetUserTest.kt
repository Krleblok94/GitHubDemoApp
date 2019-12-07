package com.kr1.krl3.domain

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.common.UseCase.None
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.repository.UserRepository
import com.kr1.krl3.domain.usecase.GetUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUserTest {

    private val userRepository = mockk<UserRepository>()
    private val user = mockk<User>()

    private val username = "Octocat"
    private val errorMessage = "Network error"

    private val getUser = GetUser(userRepository)

    @BeforeEach
    fun setUp() {
        every { user.name } returns username
    }

    @Test
    fun `when getUser succeeds, success data is in callback`() = runBlocking<Unit> {
        setUpCallback(Right(user))

        getUser(None(), this) { assertEquals(username, it.getSuccessData().name) }
    }

    @Test
    fun `when getUser fails, failure data is in callback`() = runBlocking<Unit> {
        setUpCallback(Left(NetworkFailure(errorMessage)))

        getUser(None(), this) { assertEquals(errorMessage, it.getFailureData().getMessage()) }
    }

    private fun setUpCallback(response: Either<Failure, User>) {
        coEvery { userRepository.getUser() } returns response
    }
}