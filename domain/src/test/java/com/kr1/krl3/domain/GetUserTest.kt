package com.kr1.krl3.domain

import com.kr1.krl3.domain.Either.Left
import com.kr1.krl3.domain.Either.Right
import com.kr1.krl3.domain.Failure.NetworkFailure
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
    private val getUserParams = mockk<GetUser.GetUserParams>()

    private val username = "octocat"
    private val errorMessage = "Network error"

    private val getUser = GetUser(userRepository)

    @BeforeEach
    fun setUp() {
        every { user.login } returns username
        every { getUserParams.username } returns username
    }

    @Test
    fun `when getUser succeeds, success data is in callback`() = runBlocking<Unit> {
        setUpCallback(Right(user))

        getUser(getUserParams, this) { assertEquals(username, it.getSuccessData().login) }
    }

    @Test
    fun `when getUser fails, failure data is in callback`() = runBlocking<Unit> {
        setUpCallback(Left(NetworkFailure(errorMessage)))

        getUser(getUserParams, this) { assertEquals(errorMessage, it.getFailureData().getMessage()) }
    }

    private fun setUpCallback(response: Either<Failure, User>) {
        coEvery { userRepository.getUser(any()) } returns response
    }
}