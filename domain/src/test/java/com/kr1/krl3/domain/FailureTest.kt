package com.kr1.krl3.domain

import com.kr1.krl3.domain.Failure.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FailureTest {

    @Test
    fun `when NetworkFailure is provided with a message, correct message is returned`() {
        val testMessage = "Test network failure message"
        val networkFailure = NetworkFailure(testMessage)
        Assertions.assertEquals(testMessage, networkFailure.getMessage())
    }

    @Test
    fun `when ServerFailure is provided with a message, correct message is returned`() {
        val testMessage = "Test server failure message"
        val serverFailure = ServerFailure(testMessage)
        Assertions.assertEquals(testMessage, serverFailure.getMessage())
    }

    @Test
    fun `when DatabaseFailure is provided with a message, correct message is returned`() {
        val testMessage = "Test database failure message"
        val databaseFailure = DatabaseFailure(testMessage)
        Assertions.assertEquals(testMessage, databaseFailure.getMessage())
    }
}