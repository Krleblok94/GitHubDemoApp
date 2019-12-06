package com.kr1.krl3.domain

sealed class Failure(private val message: String) {

    fun getMessage() = message

    class NetworkFailure(message: String) : Failure(message)
    class ServerFailure(message: String) : Failure(message)
    class DatabaseFailure(message: String) : Failure(message)
}