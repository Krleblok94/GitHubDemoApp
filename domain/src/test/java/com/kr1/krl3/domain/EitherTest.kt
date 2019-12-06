package com.kr1.krl3.domain

import com.kr1.krl3.domain.Either.*
import com.kr1.krl3.domain.Failure.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EitherTest {

    private val testFailureMessage = "Test failure message"
    private val testSuccessData = "Test success data"
    private val networkFailure = mockk<NetworkFailure>()

    @BeforeEach
    fun setUp() {
        every { networkFailure.getMessage() } returns testFailureMessage
    }

    @Test
    fun `check if isFailure returns true when object is of Left type`() {
        val failure = Left(networkFailure)
        Assertions.assertTrue(failure.isFailure)
    }

    @Test
    fun `check if isSuccess returns true when object is of Right type`() {
        val success = Right(testSuccessData)
        Assertions.assertTrue(success.isSuccess)
    }

    @Test
    fun `when getFailureData() is called, correct value is retrieved`() {
        val failure = Left(networkFailure)
        Assertions.assertEquals(testFailureMessage, failure.getFailureData().getMessage())
    }

    @Test
    fun `when getSuccessData() is called, correct value is retrieved`() {
        val success = Right(testSuccessData)
        Assertions.assertEquals(testSuccessData, success.getSuccessData())
    }

    @Test
    fun `when either() is called with a Left type, correct callback method is invoked`() {
        val failure = Left(networkFailure)
        var data: String? = null

        failure.either({ data = it.getMessage() }, {})
        Assertions.assertEquals(testFailureMessage, data)
    }

    @Test
    fun `when either() is called with a Right type, correct callback method is invoked`() {
        val success = Right(testSuccessData)
        var data: String? = null

        success.either({}, { data = it })
        Assertions.assertEquals(testSuccessData, data)
    }
}